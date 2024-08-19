package com.example.deskart.apiservice
import com.example.deskart.models.CarritoModel
import com.example.deskart.models.LoginModel
import com.example.deskart.models.RegistroModel
import com.example.deskart.models.modificarModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AuthApiService {
    @POST("login")
    fun postLogin(@Body params: LoginModel): Call<ResponseBody>
    @POST("loginTien")
    fun postLoginTienda(@Body params: LoginModel): Call<ResponseBody>

    @POST("registrar")
    fun postRegistrar(@Body params: RegistroModel): Call<ResponseBody>
    @POST("actualizar")
    fun putActualizar(@Body params: modificarModel): Call<ResponseBody>
    @GET("Productos/GetProductos")
    fun getProductos(): Call<ResponseBody>

    @POST("Carrito/AgregarCarrito")  // Asegúrate de que esta ruta coincida con la ruta correcta de tu API
    fun postCarrito(@Body params: CarritoModel): Call<ResponseBody>

    @GET("GetEmpleadosConRol2")
    fun getUsuariosTienda(): Call<ResponseBody>
}