window.aesUtil = {
    getKey: (key) => {
        let keyStr = '';
        for (let i = 0; i < 16 / (key.length) + 1; i++) {
            keyStr += key;
        }
        return keyStr.substr(0, 16);
    },


    encrypt: (plaintext, key) => {
        const keyStr = CryptoJS.enc.Utf8.parse(aesUtil.getKey(key));
        const srcs = CryptoJS.enc.Utf8.parse(plaintext);
        const encrypted = CryptoJS.AES.encrypt(srcs, keyStr, {mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7});
        return encrypted.toString();
    },

    decrypt: (ciphertext, key) => {
        const keyStr = CryptoJS.enc.Utf8.parse(aesUtil.getKey(key));
        const decrypt = CryptoJS.AES.decrypt(ciphertext, keyStr, {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        });
        return CryptoJS.enc.Utf8.stringify(decrypt).toString();
    }
};
