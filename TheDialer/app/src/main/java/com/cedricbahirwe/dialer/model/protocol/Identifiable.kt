package com.cedricbahirwe.dialer.model.protocol

interface Identifiable<ID: Comparable<ID>> {
    val id: ID
}

