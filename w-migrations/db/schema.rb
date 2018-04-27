# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20180425215406) do

  # These are extensions that must be enabled in order to support this database
  enable_extension "plpgsql"
  enable_extension "pgcrypto"
  enable_extension "cube"
  enable_extension "earthdistance"

  create_table "address_tbl", id: :bigserial, force: :cascade do |t|
    t.string "address1", null: false
    t.string "address2"
    t.string "city",     null: false
    t.string "state",    null: false
    t.string "zip",      null: false
  end

  create_table "merchant_tag_tbl", primary_key: ["merchant_id", "tag_id"], force: :cascade do |t|
    t.bigint "merchant_id", null: false
    t.bigint "tag_id",      null: false
  end

  create_table "merchant_tbl", id: :bigserial, force: :cascade do |t|
    t.string   "name",                                       null: false
    t.float    "latitude",                                   null: false
    t.float    "longitude",                                  null: false
    t.string   "short_description",                          null: false
    t.string   "long_description",                           null: false
    t.string   "logo",                                       null: false
    t.string   "cover_photo",                                null: false
    t.string   "phone_number"
    t.string   "email"
    t.datetime "created_at",        default: -> { "now()" }, null: false
    t.datetime "updated_at",        default: -> { "now()" }, null: false
    t.boolean  "messaging_enabled", default: true,           null: false
    t.bigint   "address_id",                                 null: false
    t.bigint   "user_id",                                    null: false
    t.index "ll_to_earth(latitude, longitude)", name: "merchant_tbl_lat_lng_idx", using: :gist
    t.index "to_tsvector('english'::regconfig, (name)::text)", name: "merchant_tbl_name_idx", using: :gin
    t.index ["user_id"], name: "merchant_tbl_user_id_idx", using: :btree
  end

  create_table "merchant_vip_tbl", primary_key: ["merchant_id", "user_id"], force: :cascade do |t|
    t.bigint "merchant_id", null: false
    t.bigint "user_id",     null: false
  end

  create_table "notice_scope_tbl", force: :cascade do |t|
    t.string "name", null: false
    t.index ["name"], name: "notice_scope_tbl_name_key", unique: true, using: :btree
  end

  create_table "notice_tbl", id: :bigserial, force: :cascade do |t|
    t.bigint   "merchant_id",                              null: false
    t.integer  "notice_scope_id",                          null: false
    t.integer  "notice_type_id",                           null: false
    t.string   "title",                                    null: false
    t.string   "text",                                     null: false
    t.string   "photo"
    t.datetime "created_at",      default: -> { "now()" }, null: false
    t.datetime "updated_at",      default: -> { "now()" }, null: false
  end

  create_table "notice_type_tbl", force: :cascade do |t|
    t.string "name", null: false
    t.index ["name"], name: "notice_type_tbl_name_key", unique: true, using: :btree
  end

  create_table "tag_group_tbl", force: :cascade do |t|
    t.string "name", null: false
    t.index ["name"], name: "tag_group_tbl_name_key", unique: true, using: :btree
  end

  create_table "tag_tbl", id: :bigserial, force: :cascade do |t|
    t.integer "tag_group_id", null: false
    t.string  "name",         null: false
    t.index "to_tsvector('english'::regconfig, (name)::text)", name: "tag_tbl_name_idx", using: :gin
    t.index ["tag_group_id", "name"], name: "tag_tbl_tag_group_id_name_idx", unique: true, using: :btree
  end

  create_table "user_favorite_tbl", primary_key: ["user_id", "merchant_id"], force: :cascade do |t|
    t.bigint "user_id",     null: false
    t.bigint "merchant_id", null: false
  end

  create_table "user_tbl", id: :bigserial, force: :cascade do |t|
    t.string   "subject",                                     null: false
    t.string   "email",                                       null: false
    t.string   "preferred_username",                          null: false
    t.string   "name",                                        null: false
    t.string   "given_name",                                  null: false
    t.string   "family_name",                                 null: false
    t.datetime "created_at",         default: -> { "now()" }, null: false
    t.datetime "updated_at",         default: -> { "now()" }, null: false
    t.boolean  "admin",              default: false,          null: false
    t.index ["admin"], name: "user_tbl_admin_idx", using: :btree
    t.index ["email"], name: "user_tbl_email_key", unique: true, using: :btree
    t.index ["subject"], name: "user_tbl_subject_idx", using: :btree
    t.index ["subject"], name: "user_tbl_subject_key", unique: true, using: :btree
  end

  add_foreign_key "merchant_tag_tbl", "merchant_tbl", column: "merchant_id", name: "merchant_tag_tbl_merchant_id_fkey", on_delete: :cascade
  add_foreign_key "merchant_tag_tbl", "tag_tbl", column: "tag_id", name: "merchant_tag_tbl_tag_id_fkey", on_delete: :cascade
  add_foreign_key "merchant_tbl", "address_tbl", column: "address_id", name: "merchant_tbl_address_id_fkey", on_delete: :restrict
  add_foreign_key "merchant_tbl", "user_tbl", column: "user_id", name: "merchant_tbl_user_id_fkey", on_delete: :cascade
  add_foreign_key "merchant_vip_tbl", "merchant_tbl", column: "merchant_id", name: "merchant_vip_tbl_merchant_id_fkey", on_delete: :cascade
  add_foreign_key "merchant_vip_tbl", "user_tbl", column: "user_id", name: "merchant_vip_tbl_user_id_fkey", on_delete: :cascade
  add_foreign_key "notice_tbl", "merchant_tbl", column: "merchant_id", name: "notice_tbl_merchant_id_fkey", on_delete: :cascade
  add_foreign_key "notice_tbl", "notice_scope_tbl", column: "notice_scope_id", name: "notice_tbl_notice_scope_id_fkey", on_delete: :restrict
  add_foreign_key "notice_tbl", "notice_type_tbl", column: "notice_type_id", name: "notice_tbl_notice_type_id_fkey", on_delete: :restrict
  add_foreign_key "tag_tbl", "tag_group_tbl", column: "tag_group_id", name: "tag_tbl_tag_group_id_fkey", on_delete: :cascade
  add_foreign_key "user_favorite_tbl", "merchant_tbl", column: "merchant_id", name: "user_favorite_tbl_merchant_id_fkey", on_delete: :cascade
  add_foreign_key "user_favorite_tbl", "user_tbl", column: "user_id", name: "user_favorite_tbl_user_id_fkey", on_delete: :cascade
end
