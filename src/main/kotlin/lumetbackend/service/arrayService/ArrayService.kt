package lumetbackend.service.arrayService

import org.springframework.stereotype.Service


@Service
class ArrayService {
    fun appendInt(array: Array<Int>, element: Int): Array<Int> {
        val list = array.toMutableList()
        list.add(element)
        return list.toTypedArray()
    }


    fun removeInt(array: Array<Int>, element: Int): Array<Int> {
        val list = array.toMutableList()
        list.remove(element)
        return list.toTypedArray()
    }

    fun appendString(array: Array<String>, element: String): Array<String> {
        val list = array.toMutableList()
        list.add(element)
        return list.toTypedArray()
    }


    fun removeString(array: Array<String>, element: String): Array<String> {
        val list = array.toMutableList()
        list.remove(element)
        return list.toTypedArray()
    }
}