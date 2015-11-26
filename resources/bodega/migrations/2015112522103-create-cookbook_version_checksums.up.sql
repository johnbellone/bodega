CREATE TABLE IF NOT EXISTS cookbook_version_checksums(
       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
       cookbook_version_id UUID REFERENCES cookbook_versions(id) ON DELETE CASCADE,
       filename TEXT NOT NULL,
       checksum TEXT NOT NULL,
       created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
       updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
       UNIQUE(cookbook_version_id, filename, checksum)
);
