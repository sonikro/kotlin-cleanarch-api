package routes

import database.UserRepositoryInMemory
import domain.entity.User
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.crud
import io.javalin.apibuilder.CrudHandler
import io.javalin.http.Context
import io.javalin.plugin.openapi.dsl.OpenApiCrudHandlerDocumentation
import io.javalin.plugin.openapi.dsl.document
import io.javalin.plugin.openapi.dsl.documentCrud
import io.javalin.plugin.openapi.dsl.documented
import model.user.*
import usecase.invoke
import usecase.user.*

fun Javalin.userRoutes() {
    val userRepository = UserRepositoryInMemory()

    val userDocumentation: OpenApiCrudHandlerDocumentation = documentCrud()
        .getAll(document().jsonArray<UserDTO>("200"))
        .getOne(document().pathParam<String>("userId").json<UserDTO>("200"))
        .create(document().body<PostUserRequest>().json<PostUserResponse>("200"))
        .update(document().pathParam<String>("userId").body<PutUserRequest>().result<Unit>("200"))
        .delete(document().pathParam<String>("userId").result<UserDTO>("200"))

    routes {
        crud("/user/:userId", documented(userDocumentation, object : CrudHandler {
            override fun create(ctx: Context) {
                ctx
                    .body<PostUserRequest>()
                    .let { body ->
                        createUserUseCase(userRepository).invoke(User(name = body.name))
                    }
                    .let { userId -> ctx.json(PostUserResponse(userId)) }
            }

            override fun delete(ctx: Context, resourceId: String) {
                deleteUserUseCase(userRepository)
                    .invoke(resourceId.toLong())
                    .let { user ->
                        ctx
                            .status(200)
                            .json(user.toDto())
                    }
            }

            override fun getAll(ctx: Context) {
                listUsersUseCase(userRepository)
                    .invoke()
                    .let { users ->
                        ctx.json(users.map { it.toDto() })
                    }
            }

            override fun getOne(ctx: Context, resourceId: String) {
                getUserUseCase(userRepository)
                    .invoke(resourceId.toLong())
                    .let { user ->
                        ctx
                            .status(200)
                            .json(user.toDto())
                    }
            }

            override fun update(ctx: Context, resourceId: String) {
                ctx
                    .body<PutUserRequest>()
                    .let { body ->
                        updateUserUseCase(userRepository).invoke(User(name = body.name, id = resourceId.toLong()))
                        ctx.status(200)
                    }
            }

        }))
    }
}