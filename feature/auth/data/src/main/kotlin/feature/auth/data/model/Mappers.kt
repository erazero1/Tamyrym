package feature.auth.data.model

import core.data.common.model.UserDTO
import core.domain.model.Gender
import feature.auth.domain.model.Role
import feature.auth.domain.model.User
import feature.auth.domain.model.UserStatus

fun UserDTO.toDomain(): User {
    return User(
        birthYear = birthYear ?: 0,
        email = email.orEmpty(),
        firstName = firstName.orEmpty(),
        gender = Gender.findValue(gender),
        id = id.orEmpty(),
        lastName = lastName.orEmpty(),
        role = Role.findRole(role),
        userStatus = UserStatus.findStatus(userStatus)
    )
}