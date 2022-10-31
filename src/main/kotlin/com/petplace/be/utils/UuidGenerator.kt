package com.petplace.be.utils

import org.apache.commons.lang3.RandomStringUtils
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator
import org.joda.time.DateTime
import java.util.*


class UuidGenerator: IdentifierGenerator {
    /**
     *
     * @return 14 byte string
     */
    fun uuid(): String? {
        val d: DateTime = DateTime.now()
        val u: UUID = UUID.randomUUID()
        val prefix: String =
            this.moreMaxRadix((d.weekyear().get() * 100 + d.weekOfWeekyear().get()).toLong())
        return prefix + moreMaxRadix(u.getLeastSignificantBits())
    }

    /**
     *
     * @return 10byte string
     */
    fun nanoUuid(): String? {
        val d: DateTime = DateTime.now()
        val prefix: String =
            this.moreMaxRadix((d.weekyear().get() * 100 + d.weekOfWeekyear().get()).toLong())
        val rand: String = RandomStringUtils.randomAlphabetic(3)
        val nano = moreMaxRadix(System.nanoTime()).substring(2)
        return prefix + rand + nano
    }

    val digits = charArrayOf(
        '0', '1', '2', '3', '4', '5',
        '6', '7', '8', '9', 'a', 'b',
        'c', 'd', 'e', 'f', 'g', 'h',
        'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F',
        'G', 'H', 'I', 'J', 'K', 'L',
        'M', 'N', 'O', 'P', 'Q', 'R',
        'S', 'T', 'U', 'V', 'W', 'X',
        'Y', 'Z'
    )

    private fun moreMaxRadix(i: Long): String {
        var i = i
        val buf = CharArray(65)
        var charPos = 64
        val negative = i < 0
        if (!negative) {
            i = -i
        }
        val radix = 62
        while (i <= -radix) {
            buf[charPos--] = digits[(-(i % radix)).toInt()]
            i = i / radix
        }
        buf[charPos] = digits[(-i).toInt()]
        return String(buf, charPos, 65 - charPos)
    }


    override fun generate(session: SharedSessionContractImplementor?, `object`: Any?): String? {
        return nanoUuid();
    }

}