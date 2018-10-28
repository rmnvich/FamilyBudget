package rmnvich.apps.familybudget.data.entity

data class Balance(var id: Int,
                   var balance: String,
                   var totalActualExpenses: String,
                   var totalPlannedExpenses: String)