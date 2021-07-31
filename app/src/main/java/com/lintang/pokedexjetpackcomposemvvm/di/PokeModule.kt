package com.lintang.pokedexjetpackcomposemvvm.di



import com.lintang.pokedexjetpackcomposemvvm.data.network.PokeApi
import com.lintang.pokedexjetpackcomposemvvm.repo.PokeRepo
import com.lintang.pokedexjetpackcomposemvvm.util.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokeModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideApi():PokeApi=Retrofit.Builder().baseUrl(BASE_URL).
    addConverterFactory(GsonConverterFactory.create()).build().create(PokeApi::class.java)

    @Provides
    @Singleton
    @JvmStatic
    fun provideRepo(pokeApi: PokeApi):PokeRepo=PokeRepo(pokeApi)

}