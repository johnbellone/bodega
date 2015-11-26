CREATE TABLE IF NOT EXISTS cookbook_versions(
       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
       cookbook_id UUID REFERENCES cookbooks(id) ON DELETE CASCADE,
       version TEXT NOT NULL,
       created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
       updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
       UNIQUE(cookbook_id, version)
);
