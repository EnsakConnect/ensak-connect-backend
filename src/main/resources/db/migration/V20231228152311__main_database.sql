-- ----------------------------
-- Sequence structure for _user_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."_user_seq";
CREATE SEQUENCE "public"."_user_seq"
    INCREMENT 50
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for certification_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."certification_seq";
CREATE SEQUENCE "public"."certification_seq"
    INCREMENT 50
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for comment_post_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."comment_post_seq";
CREATE SEQUENCE "public"."comment_post_seq"
    INCREMENT 50
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for education_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."education_seq";
CREATE SEQUENCE "public"."education_seq"
    INCREMENT 50
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for email_confirmations_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."email_confirmations_seq";
CREATE SEQUENCE "public"."email_confirmations_seq"
    INCREMENT 50
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for experience_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."experience_seq";
CREATE SEQUENCE "public"."experience_seq"
    INCREMENT 50
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for job_application_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."job_application_seq";
CREATE SEQUENCE "public"."job_application_seq"
    INCREMENT 50
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for job_post_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."job_post_seq";
CREATE SEQUENCE "public"."job_post_seq"
    INCREMENT 50
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for language_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."language_seq";
CREATE SEQUENCE "public"."language_seq"
    INCREMENT 50
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for like_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."like_seq";
CREATE SEQUENCE "public"."like_seq"
    INCREMENT 50
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for notification_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."notification_seq";
CREATE SEQUENCE "public"."notification_seq"
    INCREMENT 50
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for password_resets_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."password_resets_seq";
CREATE SEQUENCE "public"."password_resets_seq"
    INCREMENT 50
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for profile_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."profile_seq";
CREATE SEQUENCE "public"."profile_seq"
    INCREMENT 50
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for project_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."project_seq";
CREATE SEQUENCE "public"."project_seq"
    INCREMENT 50
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for question_post_answers_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."question_post_answers_seq";
CREATE SEQUENCE "public"."question_post_answers_seq"
    INCREMENT 50
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for question_posts_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."question_posts_seq";
CREATE SEQUENCE "public"."question_posts_seq"
    INCREMENT 50
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for resource_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."resource_seq";
CREATE SEQUENCE "public"."resource_seq"
    INCREMENT 50
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for skill_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."skill_seq";
CREATE SEQUENCE "public"."skill_seq"
    INCREMENT 50
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for token_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."token_seq";
CREATE SEQUENCE "public"."token_seq"
    INCREMENT 50
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Table structure for _user
-- ----------------------------
DROP TABLE IF EXISTS "public"."_user";
CREATE TABLE "public"."_user" (
                                  "id" int4 NOT NULL,
                                  "is_active" bool,
                                  "is_not_locked" bool,
                                  "activated_at" timestamp(6),
                                  "created_at" timestamp(6),
                                  "updated_at" timestamp(6),
                                  "email" varchar(255) COLLATE "pg_catalog"."default",
                                  "password" varchar(255) COLLATE "pg_catalog"."default",
                                  "role" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for certification
-- ----------------------------
DROP TABLE IF EXISTS "public"."certification";
CREATE TABLE "public"."certification" (
                                          "id" int4 NOT NULL,
                                          "profile_id" int4 NOT NULL,
                                          "created_at" timestamp(6),
                                          "updated_at" timestamp(6),
                                          "link" varchar(255) COLLATE "pg_catalog"."default",
                                          "name" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for comment_post
-- ----------------------------
DROP TABLE IF EXISTS "public"."comment_post";
CREATE TABLE "public"."comment_post" (
                                         "id" int4 NOT NULL,
                                         "job_post_id" int4 NOT NULL,
                                         "user_id" int4 NOT NULL,
                                         "created_at" timestamp(6),
                                         "updated_at" timestamp(6),
                                         "content" text COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for education
-- ----------------------------
DROP TABLE IF EXISTS "public"."education";
CREATE TABLE "public"."education" (
                                      "id" int4 NOT NULL,
                                      "profile_id" int4 NOT NULL,
                                      "created_at" timestamp(6),
                                      "end_date" timestamp(6),
                                      "start_date" timestamp(6),
                                      "updated_at" timestamp(6),
                                      "degree" varchar(255) COLLATE "pg_catalog"."default",
                                      "description" varchar(255) COLLATE "pg_catalog"."default",
                                      "field" varchar(255) COLLATE "pg_catalog"."default",
                                      "school" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for email_confirmations
-- ----------------------------
DROP TABLE IF EXISTS "public"."email_confirmations";
CREATE TABLE "public"."email_confirmations" (
                                                "id" int4 NOT NULL,
                                                "code" varchar(255) COLLATE "pg_catalog"."default",
                                                "email" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for experience
-- ----------------------------
DROP TABLE IF EXISTS "public"."experience";
CREATE TABLE "public"."experience" (
                                       "contract_type" int2,
                                       "id" int4 NOT NULL,
                                       "profile_id" int4 NOT NULL,
                                       "created_at" timestamp(6),
                                       "end_date" timestamp(6),
                                       "start_date" timestamp(6),
                                       "updated_at" timestamp(6),
                                       "company_name" varchar(255) COLLATE "pg_catalog"."default",
                                       "description" varchar(255) COLLATE "pg_catalog"."default",
                                       "location" varchar(255) COLLATE "pg_catalog"."default",
                                       "position_title" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for job_application
-- ----------------------------
DROP TABLE IF EXISTS "public"."job_application";
CREATE TABLE "public"."job_application" (
                                            "id" int4 NOT NULL,
                                            "job_post_id" int4,
                                            "user_id" int4,
                                            "created_at" timestamp(6),
                                            "updated_at" timestamp(6),
                                            "message" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for job_post
-- ----------------------------
DROP TABLE IF EXISTS "public"."job_post";
CREATE TABLE "public"."job_post" (
                                     "id" int4 NOT NULL,
                                     "user_id" int4 NOT NULL,
                                     "created_at" timestamp(6),
                                     "updated_at" timestamp(6),
                                     "category" varchar(255) COLLATE "pg_catalog"."default",
                                     "company_name" varchar(255) COLLATE "pg_catalog"."default",
                                     "company_type" varchar(255) COLLATE "pg_catalog"."default",
                                     "description" text COLLATE "pg_catalog"."default",
                                     "location" varchar(255) COLLATE "pg_catalog"."default",
                                     "title" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for job_post_likes
-- ----------------------------
DROP TABLE IF EXISTS "public"."job_post_likes";
CREATE TABLE "public"."job_post_likes" (
                                           "job_post_id" int4 NOT NULL,
                                           "likes" int4
)
;

-- ----------------------------
-- Table structure for job_post_resources
-- ----------------------------
DROP TABLE IF EXISTS "public"."job_post_resources";
CREATE TABLE "public"."job_post_resources" (
                                               "job_post_id" int4 NOT NULL,
                                               "resource_id" int4 NOT NULL
)
;

-- ----------------------------
-- Table structure for job_post_tags
-- ----------------------------
DROP TABLE IF EXISTS "public"."job_post_tags";
CREATE TABLE "public"."job_post_tags" (
                                          "job_post_id" int4 NOT NULL,
                                          "tags" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for language
-- ----------------------------
DROP TABLE IF EXISTS "public"."language";
CREATE TABLE "public"."language" (
                                     "id" int4 NOT NULL,
                                     "level" int2,
                                     "profile_id" int4 NOT NULL,
                                     "created_at" timestamp(6),
                                     "updated_at" timestamp(6),
                                     "name" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS "public"."notification";
CREATE TABLE "public"."notification" (
                                         "id" int4 NOT NULL,
                                         "user_id" int4 NOT NULL,
                                         "created_at" timestamp(6),
                                         "updated_at" timestamp(6),
                                         "category" varchar(255) COLLATE "pg_catalog"."default",
                                         "message" varchar(255) COLLATE "pg_catalog"."default",
                                         "status" varchar(255) COLLATE "pg_catalog"."default",
                                         "title" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for password_resets
-- ----------------------------
DROP TABLE IF EXISTS "public"."password_resets";
CREATE TABLE "public"."password_resets" (
                                            "id" int4 NOT NULL,
                                            "code" varchar(255) COLLATE "pg_catalog"."default",
                                            "email" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for profile
-- ----------------------------
DROP TABLE IF EXISTS "public"."profile";
CREATE TABLE "public"."profile" (
                                    "banner_id" int4,
                                    "id" int4 NOT NULL,
                                    "profile_picture_id" int4,
                                    "profile_type" int2,
                                    "resume_id" int4,
                                    "user_id" int4 NOT NULL,
                                    "created_at" timestamp(6),
                                    "updated_at" timestamp(6),
                                    "address" varchar(255) COLLATE "pg_catalog"."default",
                                    "city" varchar(255) COLLATE "pg_catalog"."default",
                                    "description" varchar(255) COLLATE "pg_catalog"."default",
                                    "full_name" varchar(255) COLLATE "pg_catalog"."default",
                                    "phone" varchar(255) COLLATE "pg_catalog"."default",
                                    "title" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS "public"."project";
CREATE TABLE "public"."project" (
                                    "id" int4 NOT NULL,
                                    "profile_id" int4 NOT NULL,
                                    "created_at" timestamp(6),
                                    "updated_at" timestamp(6),
                                    "description" varchar(255) COLLATE "pg_catalog"."default",
                                    "link" varchar(255) COLLATE "pg_catalog"."default",
                                    "name" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for question_post_answers
-- ----------------------------
DROP TABLE IF EXISTS "public"."question_post_answers";
CREATE TABLE "public"."question_post_answers" (
                                                  "id" int4 NOT NULL,
                                                  "question_post_id" int4 NOT NULL,
                                                  "user_id" int4 NOT NULL,
                                                  "created_at" timestamp(6),
                                                  "updated_at" timestamp(6),
                                                  "content" text COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for question_post_likes
-- ----------------------------
DROP TABLE IF EXISTS "public"."question_post_likes";
CREATE TABLE "public"."question_post_likes" (
                                                "likes" int4,
                                                "question_post_id" int4 NOT NULL
)
;

-- ----------------------------
-- Table structure for question_post_tags
-- ----------------------------
DROP TABLE IF EXISTS "public"."question_post_tags";
CREATE TABLE "public"."question_post_tags" (
                                               "question_post_id" int4 NOT NULL,
                                               "tags" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for question_posts
-- ----------------------------
DROP TABLE IF EXISTS "public"."question_posts";
CREATE TABLE "public"."question_posts" (
                                           "id" int4 NOT NULL,
                                           "user_id" int4 NOT NULL,
                                           "created_at" timestamp(6),
                                           "updated_at" timestamp(6),
                                           "question" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS "public"."resource";
CREATE TABLE "public"."resource" (
                                     "id" int4 NOT NULL,
                                     "used" bool NOT NULL,
                                     "user_id" int4 NOT NULL,
                                     "created_at" timestamp(6),
                                     "updated_at" timestamp(6),
                                     "filename" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Table structure for skill
-- ----------------------------
DROP TABLE IF EXISTS "public"."skill";
CREATE TABLE "public"."skill" (
                                  "id" int4 NOT NULL,
                                  "level" int2,
                                  "profile_id" int4 NOT NULL,
                                  "created_at" timestamp(6),
                                  "updated_at" timestamp(6),
                                  "name" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for token
-- ----------------------------
DROP TABLE IF EXISTS "public"."token";
CREATE TABLE "public"."token" (
                                  "expired" bool NOT NULL,
                                  "id" int4 NOT NULL,
                                  "revoked" bool NOT NULL,
                                  "user_id" int4,
                                  "token" varchar(255) COLLATE "pg_catalog"."default",
                                  "token_type" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."_user_seq"', 51, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."certification_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."comment_post_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."education_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."email_confirmations_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."experience_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."job_application_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."job_post_seq"', 51, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."language_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."like_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."notification_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."password_resets_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."profile_seq"', 51, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."project_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."question_post_answers_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."question_posts_seq"', 51, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."resource_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."skill_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."token_seq"', 1, false);

-- ----------------------------
-- Checks structure for table _user
-- ----------------------------
ALTER TABLE "public"."_user" ADD CONSTRAINT "_user_role_check" CHECK (role::text = ANY (ARRAY['ROLE_USER'::character varying, 'ROLE_ADMIN'::character varying, 'ROLE_MANAGER'::character varying]::text[]));

-- ----------------------------
-- Primary Key structure for table _user
-- ----------------------------
ALTER TABLE "public"."_user" ADD CONSTRAINT "_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table certification
-- ----------------------------
ALTER TABLE "public"."certification" ADD CONSTRAINT "certification_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table comment_post
-- ----------------------------
ALTER TABLE "public"."comment_post" ADD CONSTRAINT "comment_post_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table education
-- ----------------------------
ALTER TABLE "public"."education" ADD CONSTRAINT "education_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table email_confirmations
-- ----------------------------
ALTER TABLE "public"."email_confirmations" ADD CONSTRAINT "email_confirmations_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table experience
-- ----------------------------
ALTER TABLE "public"."experience" ADD CONSTRAINT "experience_contract_type_check" CHECK (contract_type >= 0 AND contract_type <= 4);

-- ----------------------------
-- Primary Key structure for table experience
-- ----------------------------
ALTER TABLE "public"."experience" ADD CONSTRAINT "experience_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table job_application
-- ----------------------------
ALTER TABLE "public"."job_application" ADD CONSTRAINT "job_application_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table job_post
-- ----------------------------
ALTER TABLE "public"."job_post" ADD CONSTRAINT "job_post_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table job_post_resources
-- ----------------------------
ALTER TABLE "public"."job_post_resources" ADD CONSTRAINT "job_post_resources_resource_id_key" UNIQUE ("resource_id");

-- ----------------------------
-- Checks structure for table language
-- ----------------------------
ALTER TABLE "public"."language" ADD CONSTRAINT "language_level_check" CHECK (level >= 0 AND level <= 3);

-- ----------------------------
-- Primary Key structure for table language
-- ----------------------------
ALTER TABLE "public"."language" ADD CONSTRAINT "language_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table notification
-- ----------------------------
ALTER TABLE "public"."notification" ADD CONSTRAINT "notification_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table password_resets
-- ----------------------------
ALTER TABLE "public"."password_resets" ADD CONSTRAINT "password_resets_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table profile
-- ----------------------------
ALTER TABLE "public"."profile" ADD CONSTRAINT "profile_banner_id_key" UNIQUE ("banner_id");
ALTER TABLE "public"."profile" ADD CONSTRAINT "profile_profile_picture_id_key" UNIQUE ("profile_picture_id");
ALTER TABLE "public"."profile" ADD CONSTRAINT "profile_resume_id_key" UNIQUE ("resume_id");
ALTER TABLE "public"."profile" ADD CONSTRAINT "profile_user_id_key" UNIQUE ("user_id");

-- ----------------------------
-- Checks structure for table profile
-- ----------------------------
ALTER TABLE "public"."profile" ADD CONSTRAINT "profile_profile_type_check" CHECK (profile_type >= 0 AND profile_type <= 2);

-- ----------------------------
-- Primary Key structure for table profile
-- ----------------------------
ALTER TABLE "public"."profile" ADD CONSTRAINT "profile_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table project
-- ----------------------------
ALTER TABLE "public"."project" ADD CONSTRAINT "project_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table question_post_answers
-- ----------------------------
ALTER TABLE "public"."question_post_answers" ADD CONSTRAINT "question_post_answers_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table question_posts
-- ----------------------------
ALTER TABLE "public"."question_posts" ADD CONSTRAINT "question_posts_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table resource
-- ----------------------------
ALTER TABLE "public"."resource" ADD CONSTRAINT "resource_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table skill
-- ----------------------------
ALTER TABLE "public"."skill" ADD CONSTRAINT "skill_level_check" CHECK (level >= 0 AND level <= 3);

-- ----------------------------
-- Primary Key structure for table skill
-- ----------------------------
ALTER TABLE "public"."skill" ADD CONSTRAINT "skill_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table token
-- ----------------------------
ALTER TABLE "public"."token" ADD CONSTRAINT "token_token_key" UNIQUE ("token");

-- ----------------------------
-- Checks structure for table token
-- ----------------------------
ALTER TABLE "public"."token" ADD CONSTRAINT "token_token_type_check" CHECK (token_type::text = 'BEARER'::text);

-- ----------------------------
-- Primary Key structure for table token
-- ----------------------------
ALTER TABLE "public"."token" ADD CONSTRAINT "token_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table certification
-- ----------------------------
ALTER TABLE "public"."certification" ADD CONSTRAINT "fko6ve4ysx15lc2vcjt84sal1yc" FOREIGN KEY ("profile_id") REFERENCES "public"."profile" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table comment_post
-- ----------------------------
ALTER TABLE "public"."comment_post" ADD CONSTRAINT "fk1897wl0kggtypfhe4g66aj0rg" FOREIGN KEY ("user_id") REFERENCES "public"."_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."comment_post" ADD CONSTRAINT "fkdwaq4bopi8bp58ea1k7nv6wkv" FOREIGN KEY ("job_post_id") REFERENCES "public"."job_post" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table education
-- ----------------------------
ALTER TABLE "public"."education" ADD CONSTRAINT "fkelocxwwcyf5acj85hgke1c0fl" FOREIGN KEY ("profile_id") REFERENCES "public"."profile" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table experience
-- ----------------------------
ALTER TABLE "public"."experience" ADD CONSTRAINT "fkhlkosu9yvtv1ptp01x4tfh9ut" FOREIGN KEY ("profile_id") REFERENCES "public"."profile" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table job_application
-- ----------------------------
ALTER TABLE "public"."job_application" ADD CONSTRAINT "fk69ai9dh3dnwh0a6hod6yb8o1l" FOREIGN KEY ("user_id") REFERENCES "public"."_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."job_application" ADD CONSTRAINT "fkinhp0h9tyro7khcf84h97vsy3" FOREIGN KEY ("job_post_id") REFERENCES "public"."job_post" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table job_post
-- ----------------------------
ALTER TABLE "public"."job_post" ADD CONSTRAINT "fkf43px6f3inxjh7b2rq8tlwxxx" FOREIGN KEY ("user_id") REFERENCES "public"."_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table job_post_likes
-- ----------------------------
ALTER TABLE "public"."job_post_likes" ADD CONSTRAINT "fkjup3a5pt658poenxnw2f4fmi1" FOREIGN KEY ("job_post_id") REFERENCES "public"."job_post" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table job_post_resources
-- ----------------------------
ALTER TABLE "public"."job_post_resources" ADD CONSTRAINT "fk7dsly8wtwro3ja3braeedvk0a" FOREIGN KEY ("job_post_id") REFERENCES "public"."job_post" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."job_post_resources" ADD CONSTRAINT "fkjukgwv8q07dmps4muoulxgqwf" FOREIGN KEY ("resource_id") REFERENCES "public"."resource" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table job_post_tags
-- ----------------------------
ALTER TABLE "public"."job_post_tags" ADD CONSTRAINT "fkcp4tq2bwrstod6nnufro3bgpb" FOREIGN KEY ("job_post_id") REFERENCES "public"."job_post" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table language
-- ----------------------------
ALTER TABLE "public"."language" ADD CONSTRAINT "fkge925vqwe3rkgu4ffjov1bpd0" FOREIGN KEY ("profile_id") REFERENCES "public"."profile" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table notification
-- ----------------------------
ALTER TABLE "public"."notification" ADD CONSTRAINT "fkedaeaogcqu9a9xk5oped1hnlo" FOREIGN KEY ("user_id") REFERENCES "public"."_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table profile
-- ----------------------------
ALTER TABLE "public"."profile" ADD CONSTRAINT "fk11tlnmxqccknloxj6x9tj7lsy" FOREIGN KEY ("resume_id") REFERENCES "public"."resource" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."profile" ADD CONSTRAINT "fk7bac4evgeboci836gkxx31p57" FOREIGN KEY ("profile_picture_id") REFERENCES "public"."resource" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."profile" ADD CONSTRAINT "fk7ypyk369er8i9ya1pd7ynqy1m" FOREIGN KEY ("banner_id") REFERENCES "public"."resource" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."profile" ADD CONSTRAINT "fkp08s82jrgy459anpd60iik67f" FOREIGN KEY ("user_id") REFERENCES "public"."_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table project
-- ----------------------------
ALTER TABLE "public"."project" ADD CONSTRAINT "fk2i9umkiuu36osx3afamsxq39h" FOREIGN KEY ("profile_id") REFERENCES "public"."profile" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table question_post_answers
-- ----------------------------
ALTER TABLE "public"."question_post_answers" ADD CONSTRAINT "fk9v8ft10kjfe2w1wo4jrkxc66g" FOREIGN KEY ("user_id") REFERENCES "public"."_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."question_post_answers" ADD CONSTRAINT "fkevvssio23sapwc9w5jj2nli5n" FOREIGN KEY ("question_post_id") REFERENCES "public"."question_posts" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table question_post_likes
-- ----------------------------
ALTER TABLE "public"."question_post_likes" ADD CONSTRAINT "fksr5i2x73tepjpyd25pftcguo3" FOREIGN KEY ("question_post_id") REFERENCES "public"."question_posts" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table question_post_tags
-- ----------------------------
ALTER TABLE "public"."question_post_tags" ADD CONSTRAINT "fkcxfdyekfu5hghhnlnip2hayf0" FOREIGN KEY ("question_post_id") REFERENCES "public"."question_posts" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table question_posts
-- ----------------------------
ALTER TABLE "public"."question_posts" ADD CONSTRAINT "fk9g7eu56vb3wt89ecqiih68pv9" FOREIGN KEY ("user_id") REFERENCES "public"."_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table resource
-- ----------------------------
ALTER TABLE "public"."resource" ADD CONSTRAINT "fk7g6q10mwxogljywwud56i1jau" FOREIGN KEY ("user_id") REFERENCES "public"."_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table skill
-- ----------------------------
ALTER TABLE "public"."skill" ADD CONSTRAINT "fkcaom78e5t3e2xd5qmvb2ix8xm" FOREIGN KEY ("profile_id") REFERENCES "public"."profile" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table token
-- ----------------------------
ALTER TABLE "public"."token" ADD CONSTRAINT "fkiblu4cjwvyntq3ugo31klp1c6" FOREIGN KEY ("user_id") REFERENCES "public"."_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
