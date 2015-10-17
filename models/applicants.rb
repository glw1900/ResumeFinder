class Applicant < ActiveRecord::Base
  has_many :recieves
  has_many :companies, :through => recieves
  has_many :projects
end
