package rmnvich.apps.familybudget.data.entity

data class User(var userId: Int,
                var name: String,
                var lastname: String,
                var password: String,
                var photoPath: String,
                var relationshipTypes: String,
                var incomeTypes: MutableList<IncomeType>)