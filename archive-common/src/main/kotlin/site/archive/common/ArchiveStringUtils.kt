@file:JvmName("ArchiveStringUtils")

package site.archive.common

private const val MAIL_AT = '@'

fun extractIdFromMail(mailAddress: String): String {
    return mailAddress.substring(0, mailAddress.indexOf(MAIL_AT))
}