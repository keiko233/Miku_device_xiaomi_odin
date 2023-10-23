#!/bin/bash
#
# Copyright (C) 2016 The CyanogenMod Project
# Copyright (C) 2017-2020 The LineageOS Project
#
# SPDX-License-Identifier: Apache-2.0
#

set -e

DEVICE=odin
VENDOR=xiaomi

# Load extract_utils and do some sanity checks
MY_DIR="${BASH_SOURCE%/*}"
if [[ ! -d "${MY_DIR}" ]]; then MY_DIR="${PWD}"; fi

ANDROID_ROOT="${MY_DIR}/../../.."

HELPER="${ANDROID_ROOT}/tools/extract-utils/extract_utils.sh"
if [ ! -f "${HELPER}" ]; then
    echo "Unable to find helper script at ${HELPER}"
    exit 1
fi
source "${HELPER}"

# Default to sanitizing the vendor folder before extraction
CLEAN_VENDOR=true

KANG=
SECTION=

while [ "${#}" -gt 0 ]; do
    case "${1}" in
        -n | --no-cleanup )
                CLEAN_VENDOR=false
                ;;
        -k | --kang )
                KANG="--kang"
                ;;
        -s | --section )
                SECTION="${2}"; shift
                CLEAN_VENDOR=false
                ;;
        * )
                SRC="${1}"
                ;;
    esac
    shift
done

if [ -z "${SRC}" ]; then
    SRC="adb"
fi

function blob_fixup() {
    case "${1}" in
        system_ext/etc/permissions/vendor.qti.hardware.data.connection-V1.0-java.xml|system_ext/etc/permissions/vendor.qti.hardware.data.connection-V1.1-java.xml)
            sed -i 's/version="2.0"/version="1.0"/g' "${2}"
            sed -i 's/system\/product/system_ext/g' "${2}"
            ;;
        vendor/bin/hw/dolbycodec2)
            patchelf --add-needed "libshim.so" "${2}"
            patchelf --replace-needed libcodec2_hidl@1.0.so libcodec2_hidl@1.0.stock.so "${2}"
            ;;
        vendor/etc/camera/odin_motiontuning.xml)
            sed -i 's/xml=version/xml\ version/g' "${2}"
	    ;;
        vendor/etc/camera/pureShot_parameter.xml)
            sed -i 's/=\([0-9]\+\)>/="\1">/g' "${2}"
            ;;
        vendor/etc/media_lahaina/video_system_specs.json)
            sed -i "/max_retry_alloc_output_timeout/ s/10000/0/" "${2}"
            ;;
        vendor/etc/permissions/vendor-qti-hardware-sensorscalibrate.xml)
            sed -i 's/system/system_ext/g' "${2}"
            ;;
        vendor/etc/vintf/manifest/c2_manifest_vendor.xml)
            sed -ni '/ozoaudio/!p' "${2}"
            ;;
        vendor/lib/libcodec2_hidl@1.0.stock.so)
            patchelf --set-soname libcodec2_hidl@1.0.stock.so "${2}"
            patchelf --replace-needed libcodec2_vndk.so libcodec2_vndk.stock.so "${2}"
            ;;
        vendor/lib/libcodec2_vndk.stock.so)
            patchelf --set-soname libcodec2_vndk.stock.so "${2}"
            ;;
        vendor/lib64/android.hardware.secure_element@1.0-impl.so)
            "${PATCHELF}" --remove-needed "android.hidl.base@1.0.so" "${2}"
            ;;
        vendor/lib64/hw/camera.qcom.so)
            sed -i "s/\x73\x74\x5F\x6C\x69\x63\x65\x6E\x73\x65\x2E\x6C\x69\x63/\x63\x61\x6D\x65\x72\x61\x5F\x63\x6E\x66\x2E\x74\x78\x74/g" "${2}"
            ;;
    esac
}

# Initialize the helper
setup_vendor "${DEVICE}" "${VENDOR}" "${ANDROID_ROOT}" false "${CLEAN_VENDOR}"

extract "${MY_DIR}/proprietary-files.txt" "${SRC}" "${KANG}" --section "${SECTION}"

"${MY_DIR}/setup-makefiles.sh"
