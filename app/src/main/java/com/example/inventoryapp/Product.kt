package com.example.inventoryapp

class Product{
    var proID: String? = null
    var proName: String? = null
    var proDesc: String? = null
    var proImage: String? = null

    // this is for the database
    constructor(){

    }

    constructor(proID: String, proName: String, proDesc: String, proImage: String){
        this.proID = proID
        this.proName = proName
        this.proDesc = proDesc
        this.proImage = proImage
    }
}