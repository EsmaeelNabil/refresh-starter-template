 #####  ( MVVM + MVI's States ) - Usecases Approach - Room Caching - Secrets - Dependency invertion & injection


### Features
* [Hiding secretKeys in properties Files using gradle secrets](https://github.com/google/secrets-gradle-plugin)
* [Dependencies Versions & update plugin](https://jmfayard.github.io/refreshVersions/)
* [Dagger-Hilt](https://dagger.dev/hilt/gradle-setup)
* [Usecasesapproach](https://proandroiddev.com/why-you-need-use-cases-interactors-142e8a6fe576)
* [Dependecy inversion](https://medium.com/@kedren.villena/simplifying-dependency-inversion-principle-dip-59228122649a)
 and [SOILID](https://medium.com/backticks-tildes/the-s-o-l-i-d-principles-in-pictures-b34ce2f1e898) 


---
###### to add new `Dependencies` you have two methods :
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

###### `TO ADD NEW SECRETS ` put them in `secrets.properties` file that is located in `ROOT` project folder

``` json
token_header_name="Authorization"
base_url="https://api.github.com/"
```
