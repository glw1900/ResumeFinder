class CreateModel < ActiveRecord::Migration
  def up
    create_table :applicants do |applicant|
      applicant.string :email
      applicant.string :password
      applicant.string :first_name
      applicant.string :last_name
      applicant.string :school
      applicant.string :degree
      applicant.float :gpa
    end
    create_table :companies do |company|
      company.string :email
      company.string :password
      company.string :name
      company.string :info
    end
    create_table :projects do |project|
      project.integer :appl_id
      project.string :describe
    end
    create_table :receives do |receive|
      receive.integer :appl_id
      receive.integer :comp_id
    end
  end

  def down
    drop_table :applicants
    drop_table :companies
    drop_table :projects
    drop_table :receives
  end
end
