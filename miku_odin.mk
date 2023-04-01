# Inherit from those products. Most specific first.
$(call inherit-product, $(SRC_TARGET_DIR)/product/core_64_bit.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/full_base_telephony.mk)

# Inherit some common Miku stuff
$(call inherit-product, vendor/miku/build/product/miku_product.mk)

# Boot animation
TARGET_BOOT_ANIMATION_RES := 1080

# Inherit from odin device
$(call inherit-product, $(LOCAL_PATH)/device.mk)

PRODUCT_BRAND := xiaomi
PRODUCT_DEVICE := odin
PRODUCT_MANUFACTURER := xiaomi
PRODUCT_NAME := miku_odin
PRODUCT_MODEL := 2106118C
PRODUCT_SHIPPING_API_LEVEL := 30

PRODUCT_GMS_CLIENTID_BASE := android-xiaomi
TARGET_VENDOR := xiaomi
TARGET_VENDOR_PRODUCT_NAME := odin

# Build MIKU_GAPPS
MIKU_GAPPS := true

# Maintaier
MIKU_MASTER := keiko233
