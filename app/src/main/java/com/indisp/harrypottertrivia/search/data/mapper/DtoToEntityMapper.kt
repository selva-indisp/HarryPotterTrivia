package com.indisp.harrypottertrivia.search.data.mapper

import com.indisp.harrypottertrivia.search.data.local.entity.BookEntity
import com.indisp.harrypottertrivia.search.data.local.entity.CharacterEntity
import com.indisp.harrypottertrivia.search.data.local.entity.HouseEntity
import com.indisp.harrypottertrivia.search.data.local.entity.SpellEntity
import com.indisp.harrypottertrivia.search.data.remote.dto.BookDto
import com.indisp.harrypottertrivia.search.data.remote.dto.CharacterDto
import com.indisp.harrypottertrivia.search.data.remote.dto.HouseDto
import com.indisp.harrypottertrivia.search.data.remote.dto.SpellDto

class DtoToEntityMapper {

    fun toCharacter(dto: CharacterDto) = CharacterEntity(
        id = dto.id,
        fullName = dto.fullName,
        nickName = dto.nickName,
        actorName = dto.actorName,
        imageUrl = dto.imageUrl,
        dateOfBirth = dto.dateOfBirth,
        house = dto.house
    )

    fun toBook(dto: BookDto) = BookEntity(
        id = dto.id ?: -1,
        title = dto.title,
        description = dto.description,
        releaseDate = dto.releaseDate,
        pages = dto.pages,
        coverImageUrl = dto.coverImageUrl
    )

    fun toSpell(dto: SpellDto, lastShownSpellId: Int?): SpellEntity {
        val isLastShown = if (lastShownSpellId == null) false else dto.id == lastShownSpellId

        return SpellEntity(
            id = dto.id ?: -1,
            name = dto.name,
            use = dto.use,
            isLastShown = isLastShown
        )
    }

    fun toHouse(dto: HouseDto) = HouseEntity(
        id = dto.id ?: -1,
        name = dto.name,
        founder = dto.founder,
        animal = dto.animal
    )
}