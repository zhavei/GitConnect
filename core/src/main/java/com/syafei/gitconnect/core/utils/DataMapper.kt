package com.syafei.gitconnect.core.utils

import com.syafei.gitconnect.core.data.source.localdatabase.modelentity.UsersEntity
import com.syafei.gitconnect.core.data.source.remote.modelresponse.UsersResponse
import com.syafei.gitconnect.core.domain.model.GitUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {

    fun mapResponsesListToEntities(input: List<UsersResponse>): List<UsersEntity> {
        val userList = ArrayList<UsersEntity>()
        input.map {
            val gu = UsersEntity(
                login = it.login,
                blog = it.blog,
                company = it.company,
                id = it.id ?: 0,
                publicRepos = it.publicRepos,
                followers = it.followers,
                avatarUrl = it.avatarUrl,
                htmlUrl = it.htmlUrl,
                following = it.following,
                name = it.name,
                location = it.location,
                isFavorite = false,
            )
            userList.add(gu)
        }
        return userList
    }

    //untuk save ke database
    fun mapResponseToEntities(input: UsersResponse): UsersEntity {
        return UsersEntity(
            login = input.login,
            blog = input.blog,
            company = input.company,
            id = input.id ?: 0,
            publicRepos = input.publicRepos,
            followers = input.followers,
            avatarUrl = input.avatarUrl,
            htmlUrl = input.htmlUrl,
            following = input.following,
            name = input.name,
            location = input.location,
            isFavorite = false,
        )
    }

    fun mapEntityListToDomain(input: List<UsersEntity>): List<GitUser> =
        input.map {
            GitUser(
                login = it.login,
                blog = it.blog,
                company = it.company,
                id = it.id,
                publicRepos = it.publicRepos,
                followers = it.followers,
                avatarUrl = it.avatarUrl,
                htmlUrl = it.htmlUrl,
                following = it.following,
                name = it.name,
                location = it.location,
                isFavorite = it.isFavorite
            )
        }

    fun mapEntityToDomain(input: UsersEntity): GitUser =
        GitUser(
            login = input.login,
            blog = input.blog,
            company = input.company,
            id = input.id,
            publicRepos = input.publicRepos,
            followers = input.followers,
            avatarUrl = input.avatarUrl,
            htmlUrl = input.htmlUrl,
            following = input.following,
            name = input.name,
            location = input.location,
            isFavorite = input.isFavorite
        )

    //untuk save favorite database
    fun mapDomainToEntity(input: GitUser) = UsersEntity(
        login = input.login,
        blog = input.blog,
        company = input.company,
        id = input.id ?: 0,
        publicRepos = input.publicRepos,
        followers = input.followers,
        avatarUrl = input.avatarUrl,
        htmlUrl = input.htmlUrl,
        following = input.following,
        name = input.name,
        location = input.location,
        isFavorite = input.isFavorite
    )

    fun mapResponsesToDomain(input: List<UsersResponse>): Flow<List<GitUser>> {
        val dataArray = ArrayList<GitUser>()
        input.map {
            val user = GitUser(
                login = it.login,
                blog = it.blog,
                company = it.company,
                id = it.id,
                publicRepos = it.publicRepos,
                followers = it.followers,
                avatarUrl = it.avatarUrl,
                htmlUrl = it.htmlUrl,
                following = it.following,
                name = it.name,
                location = it.location,
                isFavorite = false
            )
            dataArray.add(user)
        }
        return flowOf(dataArray)
    }

}