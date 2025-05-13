package com.example.aventurape_androidmobile.domains.management.models

public class Scholarship(
    val id: Int,
    val name: String,
    val companyName: String,
    val requirements: List<Requirement>,
    val scholarShipType: String,
    val scholarShipStatus: String,
    val coordinatorId: Int
) {
    data class Requirement(
        val name: String,
        val description: String,
        val isMandatory: Boolean
    )
}