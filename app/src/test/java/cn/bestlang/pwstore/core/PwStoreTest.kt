package cn.bestlang.pwstore.core

import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.lang.AssertionError

class PwStoreTest {

    val pwStore = PwStore.getInstance()

    @Before
    fun setUp() {
        println("setUp")
        pwStore.repo.add(Pw(
            id = 1, name = "qq", account = "125003639", pw = "129...",
            desc = "qq号码"
        ))
        pwStore.repo.add(Pw(
            id = 2, name = "wechat", account = "125003639", pw = "129...",
            desc = "微信号码"
        ))
    }

    @After
    fun tearDown() {
        println("tearDown")
        pwStore.repo.del(1)
        pwStore.repo.del(2)
    }

//    @Test
//    fun all() {
//
//        println(pwStore.repo.all())
//        assertEquals(2, pwStore.repo.all().value!!.size)
//    }

    @Test
    fun update() {
        val changePw = Pw(
            id = 2, name = "wechat-1", account = "12500", pw = "129...",
            desc = "微信号码-fix"
        )
        pwStore.repo.update(changePw)
        assertEquals(pwStore.repo.read(2), changePw)
    }

    @Test
    fun delete() {
        pwStore.repo.add(Pw(
            id = 10, name = "wechat", account = "125003639", pw = "129...",
            desc = "微信号码"
        ))
        pwStore.repo.del(10)

        try {
            pwStore.repo.read(10)
            AssertionError("delete failed")
        } catch (e : RepoException) {
        }
    }


}