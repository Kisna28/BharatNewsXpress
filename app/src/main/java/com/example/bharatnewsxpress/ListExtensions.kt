package com.example.bharatnewsxpress


    fun List<Article>.contentEquals(other: List<Article>): Boolean {
        if (this.size != other.size) return false

        for (i in this.indices) {
            if (this[i] != other[i]) return false
        }

        return true
    }
