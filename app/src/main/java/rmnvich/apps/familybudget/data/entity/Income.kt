package rmnvich.apps.familybudget.data.entity

data class Income(var incomeId: Int,
                  var value: String,
                  var userId: Int,
                  var userName: String,
                  var userRelationshipType: String,
                  var incomeType: IncomeType,
                  var timestamp: Long)