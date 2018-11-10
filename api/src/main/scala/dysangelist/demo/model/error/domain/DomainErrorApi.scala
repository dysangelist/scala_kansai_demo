package dysangelist.demo.model.error.domain

import dysangelist.demo.model.error.ErrorApi.{BadRequest, PreconditionFailed}

object DomainErrorApi {
  object TooManyBooksCheckedOutError extends BadRequest("Too many books checked out")
  object BookOverDueError extends BadRequest("You have books overdue!")
  object BookUnavailableError
      extends PreconditionFailed("Sorry, this book is not available")
}
