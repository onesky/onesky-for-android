package app.onesky.android;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

public class ApiConsumer {

    final String INVOKE_URL = "https://app-api.onesky.app/v1/";
    private String appId;
    private String apiKey;

    public ApiConsumer(String appId, String apiKey) {
        this.appId = appId;
        this.apiKey = apiKey;
    }

    public String getAppConfigContent() throws Exception {

        return getContent(INVOKE_URL + "apps/" + this.appId + "?platformId=android");
    }

    public String getStringFileContent(String languageId) throws Exception {

        return getContent(INVOKE_URL + "apps/" + this.appId + "/string-files?languageId=" + languageId + "&fileFormat=android-xml");
    }

    private String getContent(String uri) throws Exception {

        HttpClientBuilder b = HttpClientBuilder.create();

        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                return true;
            }
        }).build();
        b.setSslcontext( sslContext);

        HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslSocketFactory)
                .build();

        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager( socketFactoryRegistry);
        b.setConnectionManager( connMgr);

        HttpClient httpClient = b.build();

        HttpResponse response = httpClient.execute(new HttpGet(uri));

        return EntityUtils.toString(response.getEntity());
    }
}
