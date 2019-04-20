# **Fut-Backend**

## **What is Fut?**

```
Fut is an application for futsal-players.
...
```

---

### **_Settings_**

```java
//  If you want to run, you have to set JasyptConfig.java
//  path : /src/main/config/JasyptConfig
//  password : Contact your manager
//  algorithm : Contact your manager
//  provider : Contact your manager

@EnableEncryptableProperties
@Configuration
public class JasyptConfig {

    private static final String ENCRYPT_KEY = "{{password}}";

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(ENCRYPT_KEY);
        config.setAlgorithm("{{algorithm}}");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("{{provider}}");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config);

        return encryptor;
    }
}
```

---

### **_Code (ExampleController)_**

```
ExcampleController is sample codes of using swagger and global-exception.
You can see the example result in the below API.
GET - /examples/swagger
GET - /examples/exception

```
