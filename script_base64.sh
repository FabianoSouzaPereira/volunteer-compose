#!/bin/bash

echo "Converting keystore to base64 (no line breaks)..."
base64 -w 0 app/keystore.jks > keystore-base64.txt

echo "File generated: keystore-base64.txt"
echo "Size: $(wc -c < keystore-base64.txt) bytes"

echo -e "\n=== KEYSTORE BASE64 VERIFICATION ==="

# 1. Check original file
echo "1. Original keystore:"
ls -la app/keystore.jks

# 2. Convert again to ensure consistency
echo -e "\n2. Converting again..."
base64 -w 0 app/keystore.jks > keystore-verified.txt

# 3. Compare with existing file
echo -e "\n3. Comparing base64 files..."
if cmp -s keystore-base64.txt keystore-verified.txt; then
    echo "Base64 files are identical"
else
    echo "Base64 files are different"
    echo "Size difference:"
    echo "  Original: $(wc -c < keystore-base64.txt) bytes"
    echo "  New: $(wc -c < keystore-verified.txt) bytes"
fi

# 4. Test decoding
echo -e "\n4. Testing decoding..."
base64 -d keystore-verified.txt > decoded.jks 2>/dev/null

if [ $? -eq 0 ] && [ -s decoded.jks ]; then
    echo "Decoding successful"
    echo "Decoded size: $(wc -c < decoded.jks) bytes"

    # Compare with original
    if cmp -s app/keystore.jks decoded.jks; then
        echo "Decoded file is identical to original"
    else
        echo "Decoded file is DIFFERENT from original"
    fi
else
    echo "Decoding failed"
fi

# 5. Cleanup
rm -f decoded.jks keystore-verified.txt

echo -e "\n=== VERIFICATION COMPLETE ===\n"


# Mostrar conteúdo para copiar
echo "Copy content and set GitHub Secret ANDROID_KEYSTORE_FILE:"
echo "========================================================================"
cat keystore-base64.txt
echo ""
echo "========================================================================"