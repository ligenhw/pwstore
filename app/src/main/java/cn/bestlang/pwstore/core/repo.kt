package cn.bestlang.pwstore.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface Repo<T> {

    fun add(t: T)

    fun del(id: Int)

    fun update(t: T)

    fun read(id: Int): T

    fun all(): LiveData<List<T>>
}

class MemRepo: Repo<Pw> {

    private val lists = mutableListOf<Pw>()

    override fun add(t: Pw) {
        val pw = t.copy()
        lists.add(pw)
    }

    override fun del(id: Int) {
        val removed = lists.removeIf { it.id == id }
        if (!removed) {
            throw RepoException("delete failed, can not find id:$id .")
        }
    }

    override fun update(t: Pw) {
        val pw = t.copy()
        val removed = lists.removeIf { it.id == pw.id }
        if (removed) {
            lists.add(pw)
        } else {
            throw RepoException("update failed, can not find id:${t.id} .")
        }
    }

    override fun read(id: Int): Pw {
        val find = lists.find { it.id == id }
        if (find != null) {
            return find.copy()
        } else {
            throw RepoException("update failed, can not find id:$id .")
        }
    }

    override fun all(): LiveData<List<Pw>> {
        val pws = mutableListOf<Pw>()
        lists.forEach { pws.add(it.copy()) }
        return MutableLiveData<List<Pw>>().apply {
            value = pws
        }
    }

}