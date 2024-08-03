package com.indisp.harrypottertrivia.search.data.mapper

import com.indisp.harrypottertrivia.search.data.local.entity.BookEntity
import com.indisp.harrypottertrivia.search.data.local.entity.CharacterEntity
import com.indisp.harrypottertrivia.search.data.local.entity.HouseEntity
import com.indisp.harrypottertrivia.search.data.local.entity.SpellEntity
import com.indisp.harrypottertrivia.search.domain.model.Book
import com.indisp.harrypottertrivia.search.domain.model.Character
import com.indisp.harrypottertrivia.search.domain.model.House
import com.indisp.harrypottertrivia.search.domain.model.Spell

class EntityToDomainMapper {

    fun toBook(bookEntity: BookEntity): Book {
        return Book(
            id = bookEntity.id ?: -1,
            title = bookEntity.title.orEmpty(),
            description = bookEntity.description.orEmpty(),
            releaseDate = bookEntity.releaseDate.orEmpty(),
            pages = bookEntity.pages ?: -1,
            coverImageUrl = bookEntity.coverImageUrl.orEmpty()
        )
    }

    fun toCharacter(entity: CharacterEntity) = Character(
        id = entity.id,
        fullName = entity.fullName.orEmpty(),
        nickName = entity.nickName.orEmpty(),
        actorName = entity.actorName.orEmpty(),
        imageUrl = entity.imageUrl.orEmpty(),
        dateOfBirth = entity.dateOfBirth.orEmpty(),
        house = entity.house.orEmpty()
    )

    fun toSpell(entity: SpellEntity) = Spell(
        id = entity.id,
        name = entity.name.orEmpty(),
        use = entity.use.orEmpty()
    )

    fun toHouse(entity: HouseEntity) = House(
        id = entity.id,
        name = entity.name.orEmpty(),
        founder = entity.founder.orEmpty(),
        animal = entity.animal.orEmpty()
    )
}