package dev.tp_94.mobileapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import dev.tp_94.mobileapp.add_product.presentation.AddProductStatefulScreen
import dev.tp_94.mobileapp.add_product.presentation.AddProductViewModel
import dev.tp_94.mobileapp.basket.presentation.BasketStatefulScreen
import dev.tp_94.mobileapp.cakes_feed.presentation.CakesFeedStatefulScreen
import dev.tp_94.mobileapp.confectioner_page.presentation.ConfectionerPageStatefulScreen
import dev.tp_94.mobileapp.confectioner_page.presentation.ConfectionerPageViewModel
import dev.tp_94.mobileapp.core.SplashNavigationScreen
import dev.tp_94.mobileapp.core.models.CakeSerializerModule
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.BottomNavBar
import dev.tp_94.mobileapp.core.themes.Screen
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.core.themes.TopNameBar
import dev.tp_94.mobileapp.custom_order_settings.presentation.CustomSettingsStatefulScreen
import dev.tp_94.mobileapp.custom_order_settings.presentation.CustomSettingsViewModel
import dev.tp_94.mobileapp.customers_feed.presentation.CustomersFeedStatefulScreen
import dev.tp_94.mobileapp.login.presentation.LoginStatefulScreen
import dev.tp_94.mobileapp.main_confectioner.presentation.MainConfectionerStatefulScreen
import dev.tp_94.mobileapp.main_customer.presentation.MainStatefulScreen
import dev.tp_94.mobileapp.new_card_addition.presentation.NewCardAdditionStatefulScreen
import dev.tp_94.mobileapp.order_view.presentation.OrderViewModel
import dev.tp_94.mobileapp.order_view.presentation.OrderViewStatefulScreen
import dev.tp_94.mobileapp.orders.presentation.ConfectionerOrdersStatefulScreen
import dev.tp_94.mobileapp.orders.presentation.CustomerOrdersStatefulScreen
import dev.tp_94.mobileapp.payment.presentation.basket.BasketPaymentStatefulScreen
import dev.tp_94.mobileapp.payment.presentation.basket.BasketPaymentViewModel
import dev.tp_94.mobileapp.payment.presentation.order.OrderPaymentStatefulScreen
import dev.tp_94.mobileapp.payment.presentation.order.OrderPaymentViewModel
import dev.tp_94.mobileapp.payment_result.ErrorPayment
import dev.tp_94.mobileapp.payment_result.SuccessfulPayment
import dev.tp_94.mobileapp.product_view.presentation.ProductViewModel
import dev.tp_94.mobileapp.product_view.presentation.ProductViewStatefulScreen
import dev.tp_94.mobileapp.profile.presentation.ProfileConfectionerRoutes
import dev.tp_94.mobileapp.profile.presentation.ProfileCustomerRoutes
import dev.tp_94.mobileapp.profile.presentation.ProfileScreen
import dev.tp_94.mobileapp.profile_editor.presentation.ProfileEditorStatefulScreen
import dev.tp_94.mobileapp.self_made_cake.presentation.SelfMadeCakeStatefulScreen
import dev.tp_94.mobileapp.self_made_cake.presentation.SelfMadeCakeViewModel
import dev.tp_94.mobileapp.self_made_cake_generator.presentation.SelfMadeCakeGeneratorStatefulScreen
import dev.tp_94.mobileapp.self_made_cake_generator.presentation.SelfMadeCakeGeneratorViewModel
import dev.tp_94.mobileapp.signup.presenatation.SignUpStatefulScreen
import dev.tp_94.mobileapp.withdrawal.presentation.WithdrawalStatefulScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val isAppInitialized = mutableStateOf(false)

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        WindowCompat.setDecorFitsSystemWindows(window, true)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            !isAppInitialized.value
        }

        actionBar?.hide()

        super.onCreate(savedInstanceState)
        setContent {
            MainNavGraph(isAppInitialized)
        }
    }
}

@Composable
fun AppBottomBar(currentScreen: Screen, navController: NavController) {
    BottomNavBar(
        onMainClick = {
            if (currentScreen != Screen.MAIN)
                navController.navigate("mainCustomer") {
                    popUpTo("mainCustomer") { inclusive = true }
                }
        },
        onOrdersClick = {
            if (currentScreen != Screen.ORDERS)
                navController.navigate("customerOrders") {
                    popUpTo("customerOrders") { inclusive = true }
                }
        },
        onBasketClick = {
            if (currentScreen != Screen.BASKET)
                navController.navigate("basket") {
                    popUpTo("basket") { inclusive = true }
                }
        },
        onProfileClick = {
            if (currentScreen != Screen.PROFILE)
                navController.navigate("profile") {
                    popUpTo("profile") { inclusive = true }
                }
        },
        currentScreen = currentScreen
    )
}


@Composable
fun MainNavGraph(isAppInitialized: MutableState<Boolean>) {
    val navController = rememberNavController()
    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val background = colorResource(R.color.background)
    val lightBackground = colorResource(R.color.light_background)

    SideEffect {
        window?.statusBarColor = background.toArgb()
        window?.navigationBarColor = lightBackground.toArgb()
        WindowCompat.getInsetsController(window!!, view)
            .isAppearanceLightStatusBars = true
    }
    NavHost(
        navController,
        startDestination = "main",
        modifier = Modifier
            .padding(
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
            )
    ) {
        composable("main") {
            SplashNavigationScreen(
                onResult = {
                    when (it) {
                        is Customer -> {
                            navController.navigate("mainCustomer") {
                                popUpTo(0)
                            }
                        }

                        is Confectioner -> {
                            navController.navigate("mainConfectioner") {
                                popUpTo(0)
                            }
                        }

                        null -> {
                            navController.navigate("login") {
                                popUpTo(0)
                            }
                        }
                    }
                }
            )
        }
        composable("login") {
            LaunchedEffect(Unit) {
                isAppInitialized.value = true
            }
            Log.println(Log.INFO, "Log", "Login")
            LoginStatefulScreen(
                onSignUp = { navController.navigate("signup") },
                onSuccessCustomer = {
                    navController.navigate("mainCustomer") {
                        popUpTo(0)
                        Log.println(Log.INFO, "Log", "Navigate to profile")
                    }
                },
                onSuccessConfectioner = {
                    navController.navigate("mainConfectioner") {
                        popUpTo(0)
                        Log.println(Log.INFO, "Log", "Navigate to profile")
                    }
                },
                onSkip = {
                    navController.navigate("mainUnregistered")
                }
            )
        }
        composable("mainUnregistered") {
            LaunchedEffect(Unit) {
                isAppInitialized.value = true
            }
            MainStatefulScreen(onError = {
                navController.navigate("login") {
                    popUpTo(0)
                }
            },
                onSearch = {
                    //TODO
                },
                onNavigateToConfectioners = {
                    navController.navigate("customerfeed")
                },
                onNavigateToProducts = {
                    navController.navigate("cakeFeed")
                },
                onNavigateToConfectioner = {
                    val json = Json.encodeToString(it)
                    val encoded = URLEncoder.encode(json, "UTF-8")
                    navController.navigate("confectionerpage/$encoded")
                },
                onNavigateToProduct = {
                    val json = Json.encodeToString(it)
                    val encoded = URLEncoder.encode(json, "UTF-8")
                    navController.navigate("productView/$encoded")
                },
                bottomBar = {
                    ActiveButton(
                        onClick = {
                            navController.navigate("login") {
                                popUpTo(0)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Войти",
                            style = TextStyles.button(colorResource(R.color.light_background))
                        )
                    }
                })
        }
        composable("signup") {
            SignUpStatefulScreen(onSuccessCustomer = {
                navController.navigate("mainCustomer") {
                    popUpTo(0)
                }
            },
                onSuccessConfectioner = {
                    navController.navigate("mainConfectioner") {
                        popUpTo(0)
                        Log.println(Log.INFO, "Log", "Navigate to profile")
                    }
                },
                onSkip = {
                    navController.navigate("mainUnregistered")
                },
                onLogin = {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                }
            )
        }
        composable("basket") {
            BasketStatefulScreen(
                onOrder = {
                    val json = Json.encodeToString(it)
                    val encoded = URLEncoder.encode(json, "UTF-8")
                    navController.navigate("basketPayment/$encoded")
                },
                topBar = {
                    TopNameBar("Корзина") {
                        navController.navigateUp()
                    }
                },
                bottomBar = { AppBottomBar(Screen.BASKET, navController) }
            )
        }
        composable("mainCustomer") {
            LaunchedEffect(Unit) {
                isAppInitialized.value = true
            }
            MainStatefulScreen(onError = {
                navController.navigate("login") {
                    popUpTo(0)
                }
            },
                onSearch = {
                    //TODO
                },
                onNavigateToConfectioners = {
                    navController.navigate("customerfeed")
                },
                onNavigateToProducts = {
                    navController.navigate("cakeFeed")
                },
                onNavigateToConfectioner = {
                    val json = Json.encodeToString(it)
                    val encoded = URLEncoder.encode(json, "UTF-8")
                    navController.navigate("confectionerpage/$encoded")
                },
                onNavigateToProduct = {
                    val json = Json{ serializersModule = CakeSerializerModule.module }.encodeToString(it)
                    val encoded = URLEncoder.encode(json, "UTF-8")
                    navController.navigate("productView/$encoded")
                },
                bottomBar = {
                    AppBottomBar(currentScreen = Screen.MAIN, navController = navController)
                })
        }

        composable("mainConfectioner") {
            LaunchedEffect(Unit) {
                isAppInitialized.value = true
            }
            MainConfectionerStatefulScreen(
                onError = {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                },
                onMyProfileClick = {
                    navController.navigate("profile")
                },
                onCustomOrdersClick = {
                    navController.navigate("confectionerOrders")
                },
                onNavigateToProductEdit = {
                    val json = Json{ serializersModule = CakeSerializerModule.module }.encodeToString(it)
                    val encoded = URLEncoder.encode(json, "UTF-8")
                    navController.navigate("addProduct/$encoded")
                }
            )
        }
        composable(
            "makecake/{confectionerJson}",
            arguments = listOf(navArgument("confectionerJson") { type = NavType.StringType })
        ) {
            val viewModel: SelfMadeCakeViewModel = hiltViewModel()
            SelfMadeCakeStatefulScreen(
                viewModel = viewModel,
                onDone = {
                    navController.navigate("customerOrders") {
                        popUpTo("mainCustomer")
                    }
                },
                onError = {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                },
                onGenerate = {
                    val json = Json.encodeToString(it)
                    val encoded = URLEncoder.encode(json, "UTF-8")
                    navController.navigate("makecakegenerated/$encoded")
                },
                topBar = {
                    TopNameBar(
                        name = "Редактор торта",
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )
                },
            )
        }
        composable(
            "makecakegenerated/{confectionerJson}",
            arguments = listOf(navArgument("confectionerJson") { type = NavType.StringType })
        ) {
            val viewModel: SelfMadeCakeGeneratorViewModel = hiltViewModel()
            SelfMadeCakeGeneratorStatefulScreen(
                viewModel = viewModel,
                onDone = {
                    navController.navigate("customerOrders") {
                        popUpTo("mainCustomer")
                    }
                },
                onError = {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                },
                topBar = {
                    TopNameBar(
                        name = "Генерация торта",
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )
                },
            )
        }
        composable(
            "confectionerpage/{confectionerJson}",
            arguments = listOf(navArgument("confectionerJson") { type = NavType.StringType })
        ) {
            val viewModel: ConfectionerPageViewModel = hiltViewModel()
            ConfectionerPageStatefulScreen(
                viewModel = viewModel,
                onCakeCreation = {
                    val json = Json.encodeToString(it)
                    val encoded = URLEncoder.encode(json, "UTF-8")
                    navController.navigate("makecake/$encoded")
                },
                onNavigateToProduct = {
                    val json = Json{ serializersModule = CakeSerializerModule.module }.encodeToString(it)
                    val encoded = URLEncoder.encode(json, "UTF-8")
                    navController.navigate("productView/$encoded")
                },
                onError = {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                },
                topBar = {
                    TopNameBar("Страница кондитера") {
                        navController.navigateUp()
                    }
                },
            )
        }
        composable(
            "orderView/{order}",
            arguments = listOf(navArgument("order") { type = NavType.StringType })
        ) {
            val viewModel = hiltViewModel<OrderViewModel>()
            OrderViewStatefulScreen(
                viewModel = viewModel,
                onConfectionerClick = {
                    val json = Json.encodeToString(it)
                    val encoded = URLEncoder.encode(json, "UTF-8")
                    navController.navigate("confectionerpage/$encoded")
                },
                onError = {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                },
                topBar = {
                    TopNameBar("Заказ") {
                        navController.navigateUp()
                    }
                },
                onPay = {
                    val json =
                        Json { serializersModule = CakeSerializerModule.module }.encodeToString(it)
                    val encoded = URLEncoder.encode(json, "UTF-8")
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "paymentResult",
                        null
                    )
                    navController.navigate("orderPayment/$encoded")
                }
            )
        }

        composable(
            "orderPayment/{order}",
            arguments = listOf(navArgument("order") { type = NavType.StringType })
        ) { backStackEntry ->
            val viewModel = hiltViewModel<OrderPaymentViewModel>(backStackEntry)
            OrderPaymentStatefulScreen(
                viewModel = viewModel,
                onSuccessfulPay = {
                    navController.navigate("successfulPayment") {
                        popUpTo(navController.currentDestination?.id ?: return@navigate) {
                            inclusive = true
                        }
                    }
                },
                onErrorPay = {
                    navController.navigate("errorPayment")
                },
                onAddNewCard = {
                    navController.navigate("addNewCard")
                },
                topBar = {
                    TopNameBar(
                        name = "Оформление заказа",
                        onBackClick = { navController.navigateUp() }
                    )
                }
            )
        }

        composable(
            "basketPayment/{cakes}",
            arguments = listOf(navArgument("cakes") { type = NavType.StringType })
        ) { backStackEntry ->
            val viewModel = hiltViewModel<BasketPaymentViewModel>(backStackEntry)
            BasketPaymentStatefulScreen(
                viewModel = viewModel,
                actionButtonName = "Оплатить",
                onSuccessfulPay = {
                    navController.navigate("successfulPayment") {
                        popUpTo(navController.currentDestination?.id ?: return@navigate) {
                            inclusive = true
                        }
                    }
                },
                onErrorPay = {
                    navController.navigate("errorPayment")
                },
                onAddNewCard = {
                    navController.navigate("addNewCard")
                },
                topBar = {
                    TopNameBar(
                        name = "Оформление заказа",
                        onBackClick = { navController.navigateUp() }
                    )
                },
            )
        }

        composable("successfulPayment") {
            SuccessfulPayment({
                navController.navigateUp()
            })
        }

        composable("errorPayment") {
            ErrorPayment({
                navController.navigateUp()
            })
        }

        composable("addNewCard") {
            NewCardAdditionStatefulScreen(
                onDone = {
                    navController.navigateUp()
                },
                topBar = {
                    TopNameBar(
                        name = "Добавление новой карты",
                        onBackClick = { navController.navigateUp() },
                    )
                }
            )
        }

        composable("confectionerOrders") {
            ConfectionerOrdersStatefulScreen(
                onError = {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                },
                onNavigate = {
                    val json =
                        Json { serializersModule = CakeSerializerModule.module }.encodeToString(it)
                    val encoded = URLEncoder.encode(json, "UTF-8")
                    navController.navigate("orderView/$encoded")
                },
                topBar = {
                    TopNameBar(
                        name = "Заказы",
                        onBackClick = {
                            navController.navigateUp()
                        },
                    )
                },
            )
        }

        composable("customerOrders") {
            CustomerOrdersStatefulScreen(
                onError = {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                },
                onNavigate = {
                    val json =
                        Json { serializersModule = CakeSerializerModule.module }.encodeToString(it)
                    val encoded = URLEncoder.encode(json, "UTF-8")
                    navController.navigate("orderView/$encoded")
                },
                topBar = {
                    TopNameBar(
                        name = "Заказы",
                        onBackClick = {
                            navController.navigateUp()
                        },
                    )
                },
                onPay = {
                    val json =
                        Json { serializersModule = CakeSerializerModule.module }.encodeToString(it)
                    val encoded = URLEncoder.encode(json, "UTF-8")
                    navController.navigate("orderPayment/$encoded")
                },
                bottomBar = {
                    AppBottomBar(currentScreen = Screen.ORDERS, navController = navController)
                }
            )
        }

        composable("customerfeed") {
            CustomersFeedStatefulScreen(onNavigateToConfectioner = {
                val json = Json.encodeToString(it)
                val encoded = URLEncoder.encode(json, "UTF-8")
                navController.navigate("confectionerpage/$encoded")
            }, onBackClick = {
                navController.navigateUp()
            }, onError = {
                navController.navigate("login") {
                    popUpTo(0)
                }
            })
        }

        composable("profile") {
            Log.println(Log.INFO, "Log", "Profile")
            ProfileScreen(
                confectionerRoutes = ProfileConfectionerRoutes(
                    onChangePersonalData = {
                        navController.navigate(
                            "changeProfile"
                        )
                    },
                    onViewOrders = {
                        navController.navigate("confectionerOrders")
                    },
                    onChangeCustomCake = {
                        navController.navigate("customSettings")
                    },
                    onWithdraw = { navController.navigate("withdraw") },
                    onAddCake = {
                        navController.navigate("addProduct/null")
                    },
                ),
                customerRoutes = ProfileCustomerRoutes(onChangePersonalData = {
                    navController.navigate("changeProfile")
                },
                    onViewOrders = { navController.navigate("customerOrders") }),
                onError = {
                    Log.println(Log.INFO, "Log", "Error")
                    Log.println(Log.INFO, "Log", navController.graph.nodes.toString())
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                },
                topBar = {
                    TopNameBar(
                        name = "Профиль",
                        onBackClick = { navController.navigateUp() },
                    )
                },
                bottomBar = {
                    AppBottomBar(currentScreen = Screen.PROFILE, navController = navController)
                },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                    Log.println(Log.INFO, "Log", "Exit")
                })
        }

        composable(
            "addProduct/{cake}",
            arguments = listOf(navArgument("cake") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStackEntry ->
            val viewModel = hiltViewModel<AddProductViewModel>(backStackEntry)
            AddProductStatefulScreen(
                viewModel = viewModel,
                onMove = {
                    navController.navigateUp()
                },
                topBar = {
                    TopNameBar(
                        name = "Добавить товар",
                        onBackClick = { navController.navigateUp() }
                    )
                }
            )
        }

        composable("withdraw") {
            WithdrawalStatefulScreen(
                onAddNewCard = {
                    navController.navigate("addNewCard")
                },
                onSuccessfulWithdrawal = {
                    navController.navigate("successfulWithdrawal") {
                        popUpTo(navController.currentDestination?.id ?: return@navigate) {
                            inclusive = true
                        }
                    }
                },
                onErrorWithdrawal = {
                    navController.navigate("errorWithdrawal")
                },
                topBar = {
                    TopNameBar(
                        name = "Мои финансы",
                        onBackClick = { navController.navigateUp() },
                    )
                }
            )
        }

        composable("successfulWithdrawal") {
            SuccessfulPayment(
                onDismissRequest = {
                    navController.navigateUp()
                },
                mainText = "Все прошло успешно!",
                description = "Деньги скоро поступят",
            )
        }

        composable("errorWithdrawal") {
            ErrorPayment(
                onDismissRequest = {
                    navController.navigateUp()
                },
                mainText = "Вывод не прошел!",
                description = "Деньги всё ещё у вас.\nПожалуйста, смените способ вывода\nили попробуйте позднее",
            )
        }

        composable("changeProfile") {
            ProfileEditorStatefulScreen(onError = {
                navController.navigate("login") {
                    popUpTo(0)
                }
            }, onSave = {
                navController.navigateUp()
            }, topBar = {
                TopNameBar(name = "Личные данные", onBackClick = { navController.navigateUp() })
            })
        }

        composable("cakeFeed") {
            CakesFeedStatefulScreen(
                onNavigate = {
                    val json = Json.encodeToString(it)
                    val encoded = URLEncoder.encode(json, "UTF-8")
                    navController.navigate("productView/$encoded")
                }, onBackClick = {
                    navController.navigateUp()
                }, onError = {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                })
        }

        composable(
            "productView/{cake}",
            arguments = listOf(navArgument("cake") { type = NavType.StringType })
        ) {
            val viewModel = hiltViewModel<ProductViewModel>()
            ProductViewStatefulScreen(
                viewModel = viewModel,
                onConfectionerClick = {
                    val json = Json.encodeToString(it)
                    val encoded = URLEncoder.encode(json, "UTF-8")
                    navController.navigate("confectionerpage/$encoded")
                },
                onError = {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                },
                topBar = {
                    TopNameBar("Заказ") {
                        navController.navigateUp()
                    }
                }
            )
        }

        composable("customSettings") {
            val viewModel = hiltViewModel<CustomSettingsViewModel>()
            CustomSettingsStatefulScreen(
                viewModel = viewModel,
                onSave = {
                    navController.navigateUp()
                }, topBar = {
                    TopNameBar(name = "Кастомизация", onBackClick = { navController.navigateUp() })
                })
        }
    }
}