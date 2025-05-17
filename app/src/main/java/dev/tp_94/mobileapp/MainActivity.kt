package dev.tp_94.mobileapp

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import dev.tp_94.mobileapp.confectioner_page.presentation.ConfectionerPageStatefulScreen
import dev.tp_94.mobileapp.confectioner_page.presentation.ConfectionerPageViewModel
import dev.tp_94.mobileapp.core.SplashNavigationScreen
import dev.tp_94.mobileapp.core.models.CakeSerializerModule
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.themes.BottomNavBar
import dev.tp_94.mobileapp.core.themes.Screen
import dev.tp_94.mobileapp.core.themes.TopNameBar
import dev.tp_94.mobileapp.customers_feed.presentation.CustomersFeedStatefulScreen
import dev.tp_94.mobileapp.login.presentation.LoginStatefulScreen
import dev.tp_94.mobileapp.main_confectioner.presentation.MainConfectionerStatefulScreen
import dev.tp_94.mobileapp.main_customer.presentation.MainStatefulScreen
import dev.tp_94.mobileapp.order_payment.presentation.NewCardAdditionStatefulScreen
import dev.tp_94.mobileapp.order_payment.presentation.OrderPaymentStatefulScreen
import dev.tp_94.mobileapp.order_payment.presentation.OrderPaymentViewModel
import dev.tp_94.mobileapp.order_payment.presentation.PaymentResult
import dev.tp_94.mobileapp.order_view.presentation.OrderViewModel
import dev.tp_94.mobileapp.orders.presentation.ConfectionerOrdersStatefulScreen
import dev.tp_94.mobileapp.orders.presentation.CustomerOrdersStatefulScreen
import dev.tp_94.mobileapp.order_view.presentation.OrderViewStatefulScreen
import dev.tp_94.mobileapp.payment_result.ErrorPayment
import dev.tp_94.mobileapp.payment_result.SuccessfulPayment
import dev.tp_94.mobileapp.profile.presentation.ProfileConfectionerRoutes
import dev.tp_94.mobileapp.profile.presentation.ProfileCustomerRoutes
import dev.tp_94.mobileapp.profile.presentation.ProfileScreen
import dev.tp_94.mobileapp.profile_editor.presentation.ProfileEditorStatefulScreen
import dev.tp_94.mobileapp.self_made_cake.presentation.SelfMadeCakeStatefulScreen
import dev.tp_94.mobileapp.self_made_cake.presentation.SelfMadeCakeViewModel
import dev.tp_94.mobileapp.signup.presenatation.SignUpStatefulScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val isAppInitialized = mutableStateOf(false)

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            !isAppInitialized.value
        }

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
        onBasketClick = { /* TODO */ },
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
    NavHost(navController, startDestination = "main") {
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
                }
            )
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
                })
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
                    //TODO
                },
                onNavigateToConfectioner = {
                    val json = Json.encodeToString(it)
                    val encoded = URLEncoder.encode(json, "UTF-8")
                    navController.navigate("confectionerpage/$encoded")
                },
                onNavigateToProduct = {
                    //TODO
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
                topBar = {
                    TopNameBar(
                        name = "Редактор торта",
                        onBackClick = {
                            navController.popBackStack()
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
                onError = {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                },
                topBar = {
                    TopNameBar("Страница кондитера") {
                        navController.popBackStack()
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
                        navController.popBackStack()
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
            viewModel.initialization()
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
                        onBackClick = { navController.popBackStack() }
                    )
                }
            )
        }

        composable("successfulPayment") {
            SuccessfulPayment {
                navController.popBackStack()
            }
        }

        composable("errorPayment") {
            ErrorPayment {
                navController.popBackStack()
            }
        }

        composable("addNewCard") { backStackEntry ->
            val parentBackStackEntry = remember(backStackEntry) {
                navController.getBackStackEntry("orderPayment/{order}")
            }
            val viewModel = hiltViewModel<OrderPaymentViewModel>(parentBackStackEntry)
            NewCardAdditionStatefulScreen(
                viewModel = viewModel,
                onDone = {
                    navController.popBackStack()
                },
                topBar = {
                    TopNameBar(
                        name = "Добавление новой карты",
                        onBackClick = { navController.popBackStack() },
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
                            navController.popBackStack()
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
                            navController.popBackStack()
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
                navController.popBackStack()
            }, onError = {
                navController.navigate("login") {
                    popUpTo(0)
                }
            })
        }

        composable("profile") {
            Log.println(Log.INFO, "Log", "Profile")
            ProfileScreen(
                confectionerRoutes = ProfileConfectionerRoutes(onChangePersonalData = {
                    navController.navigate(
                        "changeProfile"
                    )
                },
                    onViewOrders = { },
                    onChangeCustomCake = { }
                ),
                customerRoutes = ProfileCustomerRoutes(onChangePersonalData = {
                    navController.navigate("changeProfile")
                },
                    onViewOrders = { }),
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
                        onBackClick = { navController.popBackStack() },
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
        composable("changeProfile") {
            ProfileEditorStatefulScreen(onError = {
                navController.navigate("login") {
                    popUpTo(0)
                }
            }, onSave = {
                navController.popBackStack()
            }, topBar = {
                TopNameBar(name = "Личные данные", onBackClick = { navController.popBackStack() })
            })
        }
    }
}