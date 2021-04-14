 ### Main and Latest Branch is `Master` branch in `Tue 16 Feb`

 - ##### `TO ADD NEW SECRETS ` put them in `keys.properties` file that is located in `ROOT` project folder
 - ##### `AND ADD KEYS, VALUES` in `build.gradle.kts` 

``` json
token_header_name="Authorization"
base_url="https://api.github.com/graphql"
github_token="bearer Github_Personal_Access_Token"
```


``` groovy
buildConfigField(stringType, "BASE_URL", props["base_url"] as String)
```

---

| Feature             |  Details                         |
:---------------------|:----------------------------------
|[Hiding secretKeys in properties Files]()|
|[Github Actions]()|
|[Dependencies Versions & update plugin]()|

---
###### to add new `Dependencies` you two ways:
- You need to change/remove [google-service.json]()
- do it like this [Video](https://youtu.be/VhYERonB8co)
- or add the dependency in `buildSrc/Libs` like this
```const val security_crypto: String = "androidx.security:security-crypto:_"```
- then add it in `build.gradle.kts` of the app  like this
```implementation(Libs.State.appState)```
###### to get latest `Dependencies` versions run this gradle command :
```
./gradlew refreshVersions
```
##### latest versions will be in [versions.properties]() to select from.
