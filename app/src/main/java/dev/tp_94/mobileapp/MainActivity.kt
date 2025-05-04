package dev.tp_94.mobileapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import dev.tp_94.mobileapp.confectionerpage.presentation.ConfectionerPageStatefulScreen
import dev.tp_94.mobileapp.confectionerpage.presentation.ConfectionerPageViewModel
import dev.tp_94.mobileapp.core.themes.BottomNavBar
import dev.tp_94.mobileapp.core.themes.Screen
import dev.tp_94.mobileapp.core.themes.TopNameBar
import dev.tp_94.mobileapp.customersfeed.presentation.CustomersFeedStatefulScreen
import dev.tp_94.mobileapp.login.presentation.LoginStatefulScreen
import dev.tp_94.mobileapp.mainconfectioner.presentation.MainConfectionerStatefulScreen
import dev.tp_94.mobileapp.maincustomer.presentation.MainStatefulScreen
import dev.tp_94.mobileapp.orders.presentation.OrdersStatefulScreen
import dev.tp_94.mobileapp.profile.presentation.ProfileConfectionerRoutes
import dev.tp_94.mobileapp.profile.presentation.ProfileCustomerRoutes
import dev.tp_94.mobileapp.profile.presentation.ProfileScreen
import dev.tp_94.mobileapp.profileeditor.presentation.ProfileEditorStatefulScreen
import dev.tp_94.mobileapp.selfmadecake.presentation.SelfMadeCakeStatefulScreen
import dev.tp_94.mobileapp.selfmadecake.presentation.SelfMadeCakeViewModel
import dev.tp_94.mobileapp.signup.presenatation.SignUpStatefulScreen
import kotlinx.coroutines.delay
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val isLoading = mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                isLoading.value
            }
        }

        setContent {
            //TODO: make a valid reason to show splash
            LaunchedEffect(Unit) {
                delay(2000)
                isLoading.value = false
            }

            MainNavGraph()
        }
    }
}

@Composable
fun MainNavGraph() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "login") {
        composable("login") {
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
            MainStatefulScreen(onError = {
                navController.navigate("login") {
                    popUpTo(0)
                }
            },
                onSearch = { TODO() },
                onNavigateToConfectioners = {
                    navController.navigate("customerfeed")
                },
                onNavigateToProducts = { TODO() },
                onNavigateToConfectioner = {
                    val json = Json.encodeToString(it)
                    val encoded = URLEncoder.encode(json, "UTF-8")
                    navController.navigate("confectionerpage/$encoded")
                },
                onNavigateToProduct = { TODO() },
                bottomBar = {
                    BottomNavBar(onMainClick = {
                        navController.navigate("main")
                    }, onOrdersClick = {
                        TODO()
                    }, onBasketClick = {
                        TODO()
                    }, onProfileClick = {
                        navController.navigate("profile")
                    }, currentScreen = Screen.MAIN
                    )
                })
        }

        composable("mainConfectioner") {
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
                    navController.popBackStack()
                },
                onError = {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                },
                topBar = {TopNameBar(
                    name = "Редактор торта",
                    onBackClick = {
                        navController.popBackStack()
                    }
                ) },
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

        composable("confectionerOrders") {
            OrdersStatefulScreen(
                onError = {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
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
            ProfileScreen(confectionerRoutes = ProfileConfectionerRoutes(onChangePersonalData = {
                navController.navigate(
                    "changeProfile"
                )
            }, onViewOrders = { }, onChangeCustomCake = { }),
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
                    BottomNavBar(onMainClick = {
                        navController.navigate("main")
                    }, onOrdersClick = { TODO() }, onProfileClick = {
                        navController.navigate("profile")
                    }, onBasketClick = { TODO() }, currentScreen = Screen.PROFILE
                    )
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