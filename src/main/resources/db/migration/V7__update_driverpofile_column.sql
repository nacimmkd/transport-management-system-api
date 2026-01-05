alter table driver_profile
alter column license_expiry_date type date using license_expiry_date::date;

