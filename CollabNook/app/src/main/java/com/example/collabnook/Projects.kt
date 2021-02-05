package com.example.collabnook

class Projects {

    var id: String? = null
    var title: String? = null
    var description: String? = null
    var domain: String? = null
    var technology: String? = null
    var collaboratorRequirements: String? = null
    var createdBy: String? = null

    constructor() {}

    constructor(id: String, title: String, description: String,domain : String, technology: String,
                collaboratorRequirements : String, createdBy : String) {
        this.id = id
        this.title = title
        this.description = description
        this.domain = domain
        this.technology = technology
        this.collaboratorRequirements = collaboratorRequirements
        this.createdBy = createdBy
    }

    constructor(title: String, description: String) {
        this.title = title
        this.description = description
    }

    fun toMap(): Map<String, Any> {

        val result = HashMap<String, Any>()
        result["title"] = title!!
        result["description"] = description!!

        return result
    }
}