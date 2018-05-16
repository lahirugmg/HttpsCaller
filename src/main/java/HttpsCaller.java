import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class HttpsCaller {

    public static void main(String[] args) {

        try {
            HttpClient client = getHttpsClient();

            HttpGet request = new HttpGet("/mypath");
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer 6e67b865d7d533b5e716c546aae4");

            HttpResponse response = client.execute(new HttpHost("myhost.com",
                    443, "https"), request);

            System.out.println("Response code : " + response.getStatusLine().getStatusCode());
            System.out.println("Response body : ");

            InputStream isResponseBody = response.getEntity().getContent();
            InputStreamReader isr = new InputStreamReader(isResponseBody);

            try (BufferedReader br = new BufferedReader(isr)) {

                String inputLine;

                while ((inputLine = br.readLine()) != null) {
                    System.out.println(inputLine);
                }
            }

        } catch (Exception e) {
            System.out.println("Error " + e.toString());
        }
    }

    private static HttpClient getHttpsClient() throws Exception {
        CloseableHttpClient client = null;

        if (client != null) {
            return client;
        }
        SSLContext sslcontext = getSSLContext();
        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslcontext,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        client = HttpClients.custom().setSSLSocketFactory(factory).build();

        return client;
    }

    private static SSLContext getSSLContext() throws KeyStoreException,
            NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException {
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());

        try (FileInputStream instream = new FileInputStream(new File("httpcallerstore.jks"))) {
            trustStore.load(instream, "storepass".toCharArray());
        }
        return SSLContexts.custom()
                .loadTrustMaterial(trustStore)
                .build();
    }
}
