package dysangelist.demo.model.error.repo

import dysangelist.demo.model.error.ErrorApi.NotFound

object Error {
  class EntityNotFoundException(entName: String)
      extends NotFound(m = s"Could not find $entName")
}
