[![kotlin](https://img.shields.io/github/languages/top/bikcodeh/ToDoApp.svg?style=for-the-badge&color=blueviolet)](https://kotlinlang.org/) [![Android API](https://img.shields.io/badge/api-26%2B-brightgreen.svg?style=for-the-badge)](https://android-arsenal.com/api?level=26)
# Meli - Challenge 
#### Main Branch: [![Build status](https://build.appcenter.ms/v0.1/apps/544ffef5-a298-4f4a-8c94-9e41c3f845d0/branches/main/badge)](https://appcenter.ms)

## :star: Features

- [x] Search products from the Mercado Libre API and display them in a list.
- [x] Show error screen if try to fetch the data and doesn't have network connection.
- [x] See the detail from a specific product.
- [x] Unit tests for domain, data and presentation(viewmodel) layers (100% coverage)
- [x] This app supports dark theme and keep state even if the device rotates

:runner: For run the app just clone the repository and execute the app on Android Studio.

### :bookmark_tabs: Requirements to install the app
- Use phones with Android Api 26+
- Having an internet connection

##### :open_file_folder: This application was developed using Kotlin and uses the following components:
- Jetpack compose
- Coroutines
- Clean architecture (Domain, Data, Presentation)
- MVVM
- App center
- Repository pattern
- Use cases
- StateFlow
- Mutable State
- Jetpack navigation compose
- Lottie animations
- Timber (Logs)
- ViewModel
- Dagger Hilt (Dependency injection)
- Coil (Load images)
- Retrofit (HTTP requests)
- Unit testing (Mockk, Thruth, Coroutines tests)

## Structure per module
#### App
- [**com**](com)
    - [**bikcodeh**](com/bikcodeh)
        - [**melichallenge**](com/bikcodeh/melichallenge)
                
### Presentation
- [**com**](com)
    - [**bikcodeh**](com/bikcodeh)
        - [**melichallenge**](com/bikcodeh/melichallenge)
            - [**presentation**](com/bikcodeh/melichallenge/presentation)
                - [**ui**](com/bikcodeh/melichallenge/presentation/ui)
                    - [**component**](com/bikcodeh/melichallenge/presentation/ui/component)
                    - [**navigation**](com/bikcodeh/melichallenge/presentation/ui/navigation)
                    - [**screens**](com/bikcodeh/melichallenge/presentation/ui/screens)
                        - [**detail**](com/bikcodeh/melichallenge/presentation/ui/screens/detail)
                        - [**home**](com/bikcodeh/melichallenge/presentation/ui/screens/home)
                        - [**splash**](com/bikcodeh/melichallenge/presentation/ui/screens/splash)
                    - [**theme**](com/bikcodeh/melichallenge/presentation/ui/theme)
                    - [**util**](com/bikcodeh/melichallenge/presentation/ui/util)
                - [**util**](com/bikcodeh/melichallenge/presentation/util)
                    - [**extension**](com/bikcodeh/melichallenge/presentation/util/extension)

#### Data
- [**com**](com)
    - [**bikcodeh**](com/bikcodeh)
        - [**melichallenge**](com/bikcodeh/melichallenge)
            - [**data**](com/bikcodeh/melichallenge/data)
                - [**di**](com/bikcodeh/melichallenge/data/di)
                - [**remote**](com/bikcodeh/melichallenge/data/remote)
                    - [**response**](com/bikcodeh/melichallenge/data/remote/response)
                    - [**service**](com/bikcodeh/melichallenge/data/remote/service)
                - [**repository**](com/bikcodeh/melichallenge/data/repository)
                - [**util**](com/bikcodeh/melichallenge/data/util)

####  Domain 
- [**com**](com)
    - [**bikcodeh**](com/bikcodeh)
        - [**melichallenge**](com/bikcodeh/melichallenge)
            - [**domain**](com/bikcodeh/melichallenge/domain)
                - [**common**](com/bikcodeh/melichallenge/domain/common)
                - [**di**](com/bikcodeh/melichallenge/domain/di)
                - [**model**](com/bikcodeh/melichallenge/domain/model)
                - [**repository**](com/bikcodeh/melichallenge/domain/repository)
                - [**usecase**](com/bikcodeh/melichallenge/domain/usecase)

#### Core test
- [**com**](com)
    - [**bikcodeh**](com/bikcodeh)
        - [**melichallenge**](com/bikcodeh/melichallenge)
            - [**core_test**](com/bikcodeh/melichallenge/core_test)
                - [**util**](com/bikcodeh/melichallenge/core_test/util)

## :sun_with_face: Screenshots Light theme
 | Splash |     Home    |  Loading  |   Products    |  Detail |
 | :----: | :---------: | :-------: | :-----------: | :-----: |
 |<img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/screenshots/splash.png" align="left" height="300" width="1600">|<img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/screenshots/home.png" align="left" height="300" width="1600">|<img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/screenshots/loading.png" align="left" height="300" width="1600">|<img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/screenshots/searched.png" align="left" height="300" width="1600">|<img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/screenshots/detail.png" align="left" height="300" width="1600"> |

| Detail Description |   Detail quantity modal  |    Detail quantity textfiel modal | Error  |
| :-----------------:| :----------------------: | :----------------:| :----------------:|
<img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/screenshots/detail_description.png" align="left" height="300" width="170">| <img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/screenshots/modal_quantity.png" align="left" height="300" width="170"> |  <img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/screenshots/modal_quantity_textfield.png" align="left" height="300" width="170"> | <img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/screenshots/error.png" align="left" height="300" width="170"> |

## :new_moon_with_face: Screenshots Dark Mode

 | Splash |     Home    |  Loading  |   Products    |  Detail |
 | :----: | :---------: | :-------: | :-----------: | :-----: |
 |<img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/screenshots/splash_dark.png" align="left" height="300" width="1600">|<img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/screenshots/home_dark.png" align="left" height="300" width="1600">|<img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/screenshots/loading_dark.png" align="left" height="300" width="1600">|<img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/screenshots/searched_dark.png" align="left" height="300" width="1600">|<img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/screenshots/detail_dark.png" align="left" height="300" width="1600">|

| Detail Description | Detail quantity modal  |    Detail quantity textfiel modal  | Error  |
| :----------------: |:----------------: |:--------------------------------: | :----------------: | 
|<img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/screenshots/detail_description_dark.png" align="left" height="300" width="170">| <img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/screenshots/modal_quantity_dark.png" align="left" height="300" width="170"> |  <img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/screenshots/modal_quantity_textfield_dark.png" align="left" height="300" width="170"> | <img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/screenshots/error_dark.png" align="left" height="300" width="170"> |

## :white_check_mark: Code coverage
### Unit testing for layers Domain, data and app (Viewmodels and Util class) with 100% coverage.
| BaseViewModel |     DetailViewModel    |  HomeViewModel  |   MeliRepository  |
| :-----------: | :--------------------: | :-------------: | :---------------: | 
|    <img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/coverage/baseviewmodel.png" align="left" height="300" width="170">           | <img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/coverage/detailviewmodel.png" align="left" height="300" width="170"> | <img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/coverage/homeviewmodel.png" align="left" height="300" width="170"> | <img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/coverage/meliRepository.png" align="left" height="300" width="170"> |

| Product Description UC |     Search Products UC    | 
| :-----------: | :--------------------: |
|    <img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/coverage/product_descrptionUC.png" align="left" height="300" width="500">           | <img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/coverage/search_productsUC.png" align="left" height="300" width="500"> |  

 | Currency Util  |   String extension  |
 | :------------: | :-----------------: | 
 | <img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/coverage/util_currencyformatter.png" align="left" height="300" width="500"> |  <img src="https://raw.githubusercontent.com/Bikcodeh/Meli-Challenge/main/assets/coverage/extension_string_encode_decode.png" align="left" height="300" width="500"> |

## :dart: Architecture

The application is built using Clean Architeture pattern based on [Architecture Components](https://developer.android.com/jetpack/guide#recommended-app-arch) on Android. The application is divided into three layers:

![Clean Arquitecture](https://devexperto.com/wp-content/uploads/2018/10/clean-architecture-own-layers.png)

- Domain: This layer contains the business logic of the application, here we define the data models and the use cases.
- Data: This layer contains the data layer of the application. It contains the database, network and the repository implementation.
- Presentation: This layer contains the presentation layer of the application.

## License

MIT

**Bikcodeh**
