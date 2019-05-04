package cn.bestlang.pwstore.core

import android.content.Context

fun createRepo(context: Context? = null): Repo<Pw> {
    val r: Repo<Pw>
    if (context == null) {
        r = MemRepo()
    } else {
        r = DbRepo().apply { init(context) }
    }

    return r
}