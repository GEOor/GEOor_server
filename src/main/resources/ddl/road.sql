 CREATE TABLE road (
  geom geometry(MultiPolygon),
  sig_cd character varying(14),
  rw_sn double precision,
  opert_de character varying(14),
  hillshade integer default 0)