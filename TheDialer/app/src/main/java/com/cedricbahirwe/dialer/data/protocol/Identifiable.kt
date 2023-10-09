package com.cedricbahirwe.dialer.data.protocol

interface Identifiable<ID: Comparable<ID>> {
    val id: ID
}

