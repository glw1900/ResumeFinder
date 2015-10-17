class Company < ActiveRecord::Base
  has_many :recieves
  has_many :applicants, :through => :recieves
end
