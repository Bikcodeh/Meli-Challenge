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

### Requirements to install the app
- Use phones with Android Api 26+
- Having an internet connection

##### This application was developed using Kotlin and uses the following components:
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
            - [**ui**](com/bikcodeh/melichallenge/ui)
                - [**component**](com/bikcodeh/melichallenge/ui/component)
                - [**navigation**](com/bikcodeh/melichallenge/ui/navigation)
                - [**screens**](com/bikcodeh/melichallenge/ui/screens)
                    - [**detail**](com/bikcodeh/melichallenge/ui/screens/detail)
                    - [**home**](com/bikcodeh/melichallenge/ui/screens/home)
                    - [**splash**](com/bikcodeh/melichallenge/ui/screens/splash)
                - [**theme**](com/bikcodeh/melichallenge/ui/theme)
                - [**util**](com/bikcodeh/melichallenge/ui/util)
            - [**util**](com/bikcodeh/melichallenge/util)
                - [**extension**](com/bikcodeh/melichallenge/util/extension)

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

## Screenshots Light theme
 | Splash |     Home    |  Loading  |   Products    |  Detail | Detail Description |
 | :----: | :---------: | :-------: | :-----------: | :-----: | :-----: |
 |![Splash](assets/screenshots/splash.png?raw=true)|![Home](assets/screenshots/home.png?raw=true)|![Loading](assets/screenshots/loading.png?raw=true)|![Products](assets/screenshots/searched.png?raw=true)|![Detail](assets/screenshots/detail.png?raw=true) |  ![Detail Description ](assets/screenshots/detail_description.png?raw=true)  |

|   Detail quantity modal  |    Detail quantity textfiel modal | Error  |
| :----------------------: | :--------------------------------: | :----------------:|
| ![Detail quantity modal](assets/screenshots/modal_quantity.png?raw=true)  |  ![Detail quantity textfiel modal](assets/screenshots/modal_quantity_textfield.png?raw=true)    | ![Error](assets/screenshots/error.png?raw=true) |

## Screenshots Dark Mode

 | Splash |     Home    |  Loading  |   Products    |  Detail | Detail Description |
 | :----: | :---------: | :-------: | :-----------: | :-----: | :-----: |
 |![Splash](assets/screenshots/splash_dark.png?raw=true)|![Home](assets/screenshots/home_dark.png?raw=true)|![Loading](assets/screenshots/loading_dark.png?raw=true)|![Products](assets/screenshots/searched_dark.png?raw=true)|![Detail](assets/screenshots/detail_dark.png?raw=true) |![Detail](assets/screenshots/detail_description_dark.png?raw=true)|

|   Detail quantity modal  |    Detail quantity textfiel modal  | Error  |
| :----------------------: | :--------------------------------: | :----------------: | 
| ![Detail quantity modal](assets/screenshots/modal_quantity_dark.png?raw=true)  |  ![Detail quantity textfiel modal](assets/screenshots/modal_quantity_textfield_dark.png?raw=true)   | ![Error](assets/screenshots/error_dark.png?raw=true) |

## :dart: Architecture

The application is built using Clean Architeture pattern based on [Architecture Components](https://developer.android.com/jetpack/guide#recommended-app-arch) on Android. The application is divided into three layers:

![Clean Arquitecture](https://devexperto.com/wp-content/uploads/2018/10/clean-architecture-own-layers.png)

- Domain: This layer contains the business logic of the application, here we define the data models and the use cases.
- Data: This layer contains the data layer of the application. It contains the database, network and the repository implementation.
- Presentation: This layer contains the presentation layer of the application.

## License

MIT

**Bikcodeh**
