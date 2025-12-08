package com.fabianospdev.volunteerscompose.core.utils

import androidx.annotation.Keep
import com.fabianospdev.volunteerscompose.BuildConfig

object Constants {
    const val PREFIX = "v1/"

    @Keep
    const val API_SERVER_NAME = "https://firestore.googleapis.com/$PREFIX"
    const val IDENTITY_API_SERVER_NAME = "https://identitytoolkit.googleapis.com/$PREFIX"

    const val PROJECT = "volunteers-3a94d"
    const val PROJECT_PARAM = "projects/$PROJECT"
    const val DATABASES = "databases"
    const val DATABASE_ID = "default"
    const val DOCUMENTS = "documents"
    const val ACCOUNTS = "accounts"

    // Placeholders para templates
    const val PLACEHOLDER_DATABASE = "{database}"
    const val PLACEHOLDER_FILE_PATH = "{filePath}"
    const val PLACEHOLDER_USER_ID = "{userId}"
    const val PLACEHOLDER_APPOINTMENT_ID = "{appointmentId}"
    const val PLACEHOLDER_INSTITUTION_ID = "{institutionId}"
    const val PLACEHOLDER_VOLUNTEER_ID = "{volunteerId}"

    // Collections
    const val COLLECTION_USERS = "users"
    const val COLLECTION_APPOINTMENTS = "appointments"
    const val COLLECTION_INSTITUTIONS = "institutions"
    const val COLLECTION_VOLUNTEERS = "volunteers"
    const val COLLECTION_SKILLS = "skills"
    const val COLLECTION_EVENTS = "events"
    const val COLLECTION_NOTIFICATIONS = "notifications"
    const val COLLECTION_REPORTS = "reports"

    fun getFirebaseApiKey(): String = BuildConfig.FIREBASE_API_KEY

    // Timeouts
    const val CONNECT_TIMEOUT = 30L
    const val READ_TIMEOUT = 20L
    const val WRITE_TIMEOUT = 20L
    const val API_RESPONSE_TEMPORARY_REDIRECT = 307

    object Endpoint {
        // Base URLs
        const val FIREBASE_AUTH_BASE = IDENTITY_API_SERVER_NAME
        const val FIRESTORE_BASE = "$API_SERVER_NAME$PROJECT_PARAM"

        // ========== AUTHENTICATION ENDPOINTS ==========
        object Auth {
            const val SIGN_UP_PATH = "$ACCOUNTS:signUp"
            const val SIGN_IN_PATH = "$ACCOUNTS:signInWithPassword"
            const val SIGN_IN_CUSTOM_TOKEN_PATH = "$ACCOUNTS:signInWithCustomToken"
            const val REFRESH_TOKEN_PATH = "token"
            const val SEND_EMAIL_VERIFICATION_PATH = "$ACCOUNTS:sendOobCode"
            const val CONFIRM_EMAIL_VERIFICATION_PATH = "$ACCOUNTS:update"
            const val SEND_PASSWORD_RESET_PATH = "$ACCOUNTS:sendOobCode"
            const val CONFIRM_PASSWORD_RESET_PATH = "$ACCOUNTS:resetPassword"
            const val CHANGE_PASSWORD_PATH = "$ACCOUNTS:update"
            const val GET_ACCOUNT_INFO_PATH = "$ACCOUNTS:lookup"
            const val DELETE_ACCOUNT_PATH = "$ACCOUNTS:delete"
            const val UPDATE_PROFILE_PATH = "$ACCOUNTS:update"

            // Funções para construir URLs completas
            fun getSignUpUrl(apiKey: String = getFirebaseApiKey()): String {
                return "$FIREBASE_AUTH_BASE$SIGN_UP_PATH?key=$apiKey"
            }

            fun getSignInUrl(apiKey: String = getFirebaseApiKey()): String {
                return "$FIREBASE_AUTH_BASE$SIGN_IN_PATH?key=$apiKey"
            }

            fun getSignInWithCustomTokenUrl(apiKey: String = getFirebaseApiKey()): String {
                return "$FIREBASE_AUTH_BASE$SIGN_IN_CUSTOM_TOKEN_PATH?key=$apiKey"
            }

            fun getRefreshTokenUrl(apiKey: String = getFirebaseApiKey()): String {
                return "$FIREBASE_AUTH_BASE$REFRESH_TOKEN_PATH?key=$apiKey"
            }

            fun getSendEmailVerificationUrl(apiKey: String = getFirebaseApiKey()): String {
                return "$FIREBASE_AUTH_BASE$SEND_EMAIL_VERIFICATION_PATH?key=$apiKey"
            }

            fun getSendPasswordResetUrl(apiKey: String = getFirebaseApiKey()): String {
                return "$FIREBASE_AUTH_BASE$SEND_PASSWORD_RESET_PATH?key=$apiKey"
            }
        }

        // ========== FIRESTORE DATABASE ENDPOINTS ==========
        object Database {
            private const val BASE_DOCUMENTS_TEMPLATE = "$FIRESTORE_BASE/$DATABASES/$PLACEHOLDER_DATABASE/$DOCUMENTS"

            // Função para substituir placeholders
            private fun buildBaseDocuments(database: String = DATABASE_ID): String {
                return BASE_DOCUMENTS_TEMPLATE.replace(PLACEHOLDER_DATABASE, database)
            }

            // Users Collection
            object Users {
                const val COLLECTION_PATH = COLLECTION_USERS
                private fun getBase(database: String = DATABASE_ID): String {
                    return "${buildBaseDocuments(database)}/$COLLECTION_PATH"
                }

                fun getUser(database: String = DATABASE_ID, userId: String): String {
                    return "${getBase(database)}/$userId"
                }

                fun createUser(database: String = DATABASE_ID): String {
                    return getBase(database)
                }

                fun updateUser(database: String = DATABASE_ID, userId: String): String {
                    return "${getBase(database)}/$userId"
                }

                fun deleteUser(database: String = DATABASE_ID, userId: String): String {
                    return "${getBase(database)}/$userId"
                }

                fun listUsers(database: String = DATABASE_ID): String {
                    return getBase(database)
                }

                fun queryUsers(database: String = DATABASE_ID): String {
                    return "${getBase(database)}:runQuery"
                }

                fun batchGet(database: String = DATABASE_ID): String {
                    return "${getBase(database)}:batchGet"
                }
            }

            // Appointments Collection
            object Appointments {
                const val COLLECTION_PATH = COLLECTION_APPOINTMENTS
                private fun getBase(database: String = DATABASE_ID): String {
                    return "${buildBaseDocuments(database)}/$COLLECTION_PATH"
                }

                fun getAppointment(database: String = DATABASE_ID, appointmentId: String): String {
                    return "${getBase(database)}/$appointmentId"
                }

                fun createAppointment(database: String = DATABASE_ID): String {
                    return getBase(database)
                }

                fun updateAppointment(database: String = DATABASE_ID, appointmentId: String): String {
                    return "${getBase(database)}/$appointmentId"
                }

                fun deleteAppointment(database: String = DATABASE_ID, appointmentId: String): String {
                    return "${getBase(database)}/$appointmentId"
                }

                fun listAppointments(database: String = DATABASE_ID): String {
                    return getBase(database)
                }

                fun queryAppointments(database: String = DATABASE_ID): String {
                    return "${getBase(database)}:runQuery"
                }
            }

            // Institutions Collection
            object Institutions {
                const val COLLECTION_PATH = COLLECTION_INSTITUTIONS
                private fun getBase(database: String = DATABASE_ID): String {
                    return "${buildBaseDocuments(database)}/$COLLECTION_PATH"
                }

                fun getInstitution(database: String = DATABASE_ID, institutionId: String): String {
                    return "${getBase(database)}/$institutionId"
                }

                fun createInstitution(database: String = DATABASE_ID): String {
                    return getBase(database)
                }

                fun updateInstitution(database: String = DATABASE_ID, institutionId: String): String {
                    return "${getBase(database)}/$institutionId"
                }

                fun deleteInstitution(database: String = DATABASE_ID, institutionId: String): String {
                    return "${getBase(database)}/$institutionId"
                }

                fun listInstitutions(database: String = DATABASE_ID): String {
                    return getBase(database)
                }

                fun queryInstitutions(database: String = DATABASE_ID): String {
                    return "${getBase(database)}:runQuery"
                }
            }

            // Volunteers Collection
            object Volunteers {
                const val COLLECTION_PATH = COLLECTION_VOLUNTEERS
                private fun getBase(database: String = DATABASE_ID): String {
                    return "${buildBaseDocuments(database)}/$COLLECTION_PATH"
                }

                fun getVolunteer(database: String = DATABASE_ID, volunteerId: String): String {
                    return "${getBase(database)}/$volunteerId"
                }

                fun createVolunteer(database: String = DATABASE_ID): String {
                    return getBase(database)
                }

                fun updateVolunteer(database: String = DATABASE_ID, volunteerId: String): String {
                    return "${getBase(database)}/$volunteerId"
                }

                fun deleteVolunteer(database: String = DATABASE_ID, volunteerId: String): String {
                    return "${getBase(database)}/$volunteerId"
                }

                fun listVolunteers(database: String = DATABASE_ID): String {
                    return getBase(database)
                }

                fun queryVolunteers(database: String = DATABASE_ID): String {
                    return "${getBase(database)}:runQuery"
                }
            }

            // Batch Operations
            object Batch {
                fun commit(database: String = DATABASE_ID): String {
                    return "$FIRESTORE_BASE/$DATABASES/$database/$DOCUMENTS:commit"
                }

                fun batchGet(database: String = DATABASE_ID): String {
                    return "$FIRESTORE_BASE/$DATABASES/$database/$DOCUMENTS:batchGet"
                }

                fun batchWrite(database: String = DATABASE_ID): String {
                    return "$FIRESTORE_BASE/$DATABASES/$database/$DOCUMENTS:batchWrite"
                }
            }
        }

        // ========== UTILITY FUNCTIONS ==========
        object Utils {
            // Build dynamic paths
            fun buildDocumentPath(
                database: String = DATABASE_ID,
                collection: String,
                documentId: String
            ): String {
                return "$API_SERVER_NAME$PROJECT_PARAM/$DATABASES/$database/$DOCUMENTS/$collection/$documentId"
            }

            fun buildCollectionPath(
                database: String = DATABASE_ID,
                collection: String
            ): String {
                return "$API_SERVER_NAME$PROJECT_PARAM/$DATABASES/$database/$DOCUMENTS/$collection"
            }

            fun buildQueryPath(
                database: String = DATABASE_ID,
                collection: String
            ): String {
                return "${buildCollectionPath(database, collection)}:runQuery"
            }

            // Auth URL builders
            fun buildAuthUrl(endpoint: String, apiKey: String = getFirebaseApiKey()): String {
                return "$IDENTITY_API_SERVER_NAME$endpoint?key=$apiKey"
            }
        }
    }

    // ========== FIREBASE STORAGE CONSTANTS ==========
    object Storage {
        const val BUCKET_NAME = "volunteers-3a94d.appspot.com"
        const val BASE_URL = "https://firebasestorage.googleapis.com/v0/b/$BUCKET_NAME/o/"

        object Paths {
            const val USER_PROFILES = "user_profiles/%s"
            const val INSTITUTION_LOGOS = "institution_logos/%s"
            const val EVENT_IMAGES = "event_images/%s"
            const val REPORT_ATTACHMENTS = "report_attachments/%s"
            const val DOCUMENTS = "documents/%s"
        }

        object Endpoint {
            const val UPLOAD_TEMPLATE = "$BASE_URL$PLACEHOLDER_FILE_PATH"
            const val DOWNLOAD_TEMPLATE = "$BASE_URL$PLACEHOLDER_FILE_PATH?alt=media"
            const val DELETE_TEMPLATE = "$BASE_URL$PLACEHOLDER_FILE_PATH"

            // Função genérica para substituir placeholders
            private fun replacePlaceholder(template: String, replacement: String): String {
                return template.replace(PLACEHOLDER_FILE_PATH, replacement)
            }

            fun getUploadUrl(filePath: String): String {
                return replacePlaceholder(UPLOAD_TEMPLATE, filePath)
            }

            fun getDownloadUrl(filePath: String): String {
                return replacePlaceholder(DOWNLOAD_TEMPLATE, filePath)
            }

            fun getDeleteUrl(filePath: String): String {
                return replacePlaceholder(DELETE_TEMPLATE, filePath)
            }
        }
    }

    // ========== FIREBASE CLOUD FUNCTIONS ==========
    object CloudFunctions {
        const val PROJECT_ID = "volunteers-3a94d"
        const val REGION = "us-central1"
        const val BASE_URL = "https://$REGION-$PROJECT_ID.cloudfunctions.net/"

        object Endpoint {
            // User Management
            const val ON_USER_CREATE = "${BASE_URL}onUserCreate"
            const val ON_USER_UPDATE = "${BASE_URL}onUserUpdate"
            const val ON_USER_DELETE = "${BASE_URL}onUserDelete"

            // Appointment Management
            const val CREATE_APPOINTMENT = "${BASE_URL}createAppointment"
            const val CANCEL_APPOINTMENT = "${BASE_URL}cancelAppointment"
            const val UPDATE_APPOINTMENT_STATUS = "${BASE_URL}updateAppointmentStatus"
            const val SEND_APPOINTMENT_REMINDER = "${BASE_URL}sendAppointmentReminder"

            // Utility Functions
            const val BACKUP_DATA = "${BASE_URL}backupData"
        }
    }

    // ========== QUERY PARAMETERS ==========
    object QueryParams {
        // Pagination
        const val PAGE_SIZE = "pageSize"
        const val PAGE_TOKEN = "pageToken"
        const val ORDER_BY = "orderBy"

        // Filtering
        const val FILTER = "filter"
        const val LIMIT = "limit"
        const val OFFSET = "offset"

        // Field selection
        const val MASK_FIELD_PATHS = "mask.fieldPaths"

        // Auth
        const val KEY = "key"
        const val ID_TOKEN = "idToken"
        const val REFRESH_TOKEN = "refreshToken"
    }

    // ========== REQUEST HEADERS ==========
    object Headers {
        // Authorization
        const val AUTHORIZATION = "Authorization"
        const val BEARER_PREFIX = "Bearer "

        // Content Types
        const val CONTENT_TYPE = "Content-Type"
        const val APPLICATION_JSON = "application/json"

        // Firestore specific
        const val X_GOOG_API_CLIENT = "X-Goog-Api-Client"
    }

    // ========== ERROR CODES ==========
    object ErrorCodes {
        // Firebase Auth Errors
        const val EMAIL_EXISTS = "EMAIL_EXISTS"
        const val EMAIL_NOT_FOUND = "EMAIL_NOT_FOUND"
        const val INVALID_PASSWORD = "INVALID_PASSWORD"
        const val USER_DISABLED = "USER_DISABLED"
        const val TOKEN_EXPIRED = "TOKEN_EXPIRED"

        // Firestore Errors
        const val NOT_FOUND = "NOT_FOUND"
        const val PERMISSION_DENIED = "PERMISSION_DENIED"
        const val INVALID_ARGUMENT = "INVALID_ARGUMENT"
    }
}