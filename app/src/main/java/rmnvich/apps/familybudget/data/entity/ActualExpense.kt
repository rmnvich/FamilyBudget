package rmnvich.apps.familybudget.data.entity

data class ActualExpense(var expenseId: Int,
                         var value: String,
                         var userId: Int,
                         var userName: String,
                         var userRelationshipType: String,
                         var category: Category,
                         var timestamp: Long)