**HTTPS Caller**

Create a key store using keytool

`$ keytool -genkey -keyalg RSA -alias httpcallerstore
       -keypass keypass -keystore httpcallerstore.jks -storepass
       storepass -validity 365`

Import the certificate to the key store

`$ keytool -import -alias httpcallercert -file DigiRoot.cer -keystore httpcallerstore.jks -storepass storepass`