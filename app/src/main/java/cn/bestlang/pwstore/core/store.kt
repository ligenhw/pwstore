package cn.bestlang.pwstore.core

import android.content.Context

class PwStore private constructor(ctx: Context? = null){

    var repo : Repo<Pw> = createRepo(ctx)

    companion object {

        var instance: PwStore? = null

        fun getInstance(ctx: Context? = null): PwStore {
            if (instance == null) {
                synchronized(PwStore::class) {
                    if (instance == null) {
                        instance = PwStore(ctx)
                    }
                }
            }
            return instance!!
        }
    }
}