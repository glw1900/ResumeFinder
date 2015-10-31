# encoding: UTF-8
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

ActiveRecord::Schema.define(version: 20151017030730) do

  create_table "applicants", force: :cascade do |t|
    t.string "email"
    t.string "password"
    t.string "first_name"
    t.string "last_name"
    t.string "summary"
  end

  create_table "companies", force: :cascade do |t|
    t.string "email"
    t.string "password"
    t.string "name"
    t.string "info"
  end

  create_table "educations", force: :cascade do |t|
    t.integer "appl_id"
    t.string  "school"
    t.string  "degree"
    t.string  "major"
    t.float   "gpa"
    t.string  "description"
    t.date    "start_date"
    t.date    "end_date"
  end

  create_table "experiences", force: :cascade do |t|
    t.integer "appl_id"
    t.string  "title"
    t.string  "description"
    t.date    "start_date"
    t.date    "end_date"
  end

  create_table "projects", force: :cascade do |t|
    t.integer "appl_id"
    t.string  "title"
    t.date    "start_date"
    t.date    "end_date"
    t.string  "description"
  end

  create_table "receives", force: :cascade do |t|
    t.integer "appl_id"
    t.integer "comp_id"
  end

end
