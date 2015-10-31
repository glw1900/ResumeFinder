class CreateModel < ActiveRecord::Migration
  def up
    create_table :applicants do |applicant|
      applicant.string :email
      applicant.string :password
      applicant.string :first_name
      applicant.string :last_name
      applicant.string :summary
    end

    create_table :companies do |company|
      company.string :email
      company.string :password
      company.string :name
      company.string :info
    end

    create_table :projects do |project|
      project.integer :appl_id
      project.string :title
      project.date :start_date
      project.date :end_date
      project.string :description
    end

    create_table :educations do |education|
      education.integer :appl_id
      education.string :school
      education.string :degree
      education.string :major
      education.float :gpa
      education.string :description
      education.date :start_date
      education.date :end_date
    end

    create_table :experiences do |experience|
      experience.integer :appl_id
      experience.string :title
      experience.string :description
      experience.date :start_date
      experience.date :end_date
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
    drop_table :educations
    drop_table :experiences
    drop_table :receives
  end
end
