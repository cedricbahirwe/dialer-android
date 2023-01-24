package com.cedricbahirwe.dialer.model

interface Identifiable<ID: Comparable<ID>> {
    val id: ID
}

