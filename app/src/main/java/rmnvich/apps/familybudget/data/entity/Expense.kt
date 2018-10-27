package rmnvich.apps.familybudget.data.entity

data class Expense(var expenseId: Int,
                   var value: String,
                   var comment: String,
                   var userId: Int,
                   var userName: String,
                   var userRelationshipType: String,
                   var category: Category,
                   var timestamp: Long,
                   var isPlannedExpense: Boolean) {

    var plannedUntil: Long? = 0L
}