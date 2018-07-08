@Configuration
@EnableElasticsearchRepositories(basePackages="com.elasticSpring.Connectivity.repository")
public class ElasticConfig {
	
	@Value("${spring.data.elastic_host}")
	private String elastic_host;
	
	@Value("${spring.data.elastic_port}")
	private int elastic_port;
	
	@Value("${spring.data.elastic_username}")
	private String elastic_user;
	
	@Value("${spring.data.elastic_password}")
	private String elastic_password;
	
	@Bean
	public RestClient restClient() throws UnknownHostException {
		Header[] headers = { new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json")};
		
		final CredentialsProvider credentials = new BasicCredentialsProvider();
		credentials.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(elastic_user,elastic_password));
		
		RestClientBuilder clientBuilder = RestClient.builder(new HttpHost(InetAddress.getByName(elastic_host),elastic_port)).setDefaultHeaders(headers);
		
		clientBuilder.setHttpClientConfigCallback(new HttpClientConfigCallback() {
			public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
				return httpClientBuilder.setDefaultCredentialsProvider(credentials);
				//return httpClientBuilder.setProxy(new HttpHost("proxy", 9000, "http"));
			}
		});
		
		clientBuilder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                return requestConfigBuilder.setConnectTimeout(20000)
                        .setSocketTimeout(60000);
            }
        })
        .setMaxRetryTimeoutMillis(60000);
		
		
		return clientBuilder.build();
	}
	
}
